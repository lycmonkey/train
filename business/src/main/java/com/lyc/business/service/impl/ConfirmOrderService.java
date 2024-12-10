package com.lyc.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.business.domain.DailyTrainTicket;
import com.lyc.business.enums.ConfirmOrderStatusEnum;
import com.lyc.business.enums.SeatTypeEnum;
import com.lyc.business.req.ConfirmOrderTicketReq;
import com.lyc.common.context.LoginMemberContext;
import com.lyc.common.exception.BusinessException;
import com.lyc.common.exception.BusinessExceptionEnum;
import com.lyc.common.resp.PageResp;
import com.lyc.common.util.SnowUtil;
import com.lyc.business.domain.ConfirmOrder;
import com.lyc.business.domain.ConfirmOrderExample;
import com.lyc.business.mapper.ConfirmOrderMapper;
import com.lyc.business.req.ConfirmOrderQueryReq;
import com.lyc.business.req.ConfirmOrderDoReq;
import com.lyc.business.resp.ConfirmOrderQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ConfirmOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderService.class);

    @Resource
    private ConfirmOrderMapper confirmOrderMapper;
    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    public void save(ConfirmOrderDoReq req) {
        DateTime now = DateTime.now();
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(req, ConfirmOrder.class);
        if (ObjectUtil.isNull(confirmOrder.getId())) {
            confirmOrder.setId(SnowUtil.getSnowflakeNextId());
            confirmOrder.setCreateTime(now);
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.insert(confirmOrder);
        } else {
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.updateByPrimaryKey(confirmOrder);
        }
    }

    public PageResp<ConfirmOrderQueryResp> queryList(ConfirmOrderQueryReq req) {
        ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
        confirmOrderExample.setOrderByClause("id desc");
        ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<ConfirmOrder> confirmOrderList = confirmOrderMapper.selectByExample(confirmOrderExample);

        PageInfo<ConfirmOrder> pageInfo = new PageInfo<>(confirmOrderList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<ConfirmOrderQueryResp> list = BeanUtil.copyToList(confirmOrderList, ConfirmOrderQueryResp.class);

        PageResp<ConfirmOrderQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        confirmOrderMapper.deleteByPrimaryKey(id);
    }


    @Transactional
    public void doConfirm(ConfirmOrderDoReq req) {
        LOG.info("省略业务数据校验，如：车次是否存在，余票是否存在，车次是否在有效期内，tickets条数>0，同乘客同车次是否已买过");

        Date now = new Date();
        final Date date = req.getDate();
        final String start = req.getStart();
        final String end = req.getEnd();
        final String trainCode = req.getTrainCode();
//        保存确认订单表，状态初始
        ConfirmOrder confirmOrder = new ConfirmOrder();
        BeanUtil.copyProperties(req, confirmOrder);
        confirmOrder.setId(SnowUtil.getSnowflakeNextId());
        confirmOrder.setMemberId(LoginMemberContext.getMemberId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
        confirmOrder.setTickets(JSONUtil.toJsonStr(req.getTickets()));
        confirmOrder.setCreateTime(now);
        confirmOrder.setUpdateTime(now);
        confirmOrderMapper.insert(confirmOrder);
//        查出余票记录，需要得到真实的库存
        final DailyTrainTicket dailyTrainTicket = dailyTrainTicketService.selectByUnique(date, trainCode, start, end);
        LOG.info("查出余票记录：{}", dailyTrainTicket);

//        扣减余票数量，并判断余票是否足够
        final List<ConfirmOrderTicketReq> tickets = req.getTickets();
        for (ConfirmOrderTicketReq ticket : tickets) {
            final String seatType = ticket.getSeatTypeCode();
            final SeatTypeEnum seatTypeEnum = EnumUtil.getBy(SeatTypeEnum::getCode, seatType);
            switch (seatTypeEnum) {
                case YDZ -> {
                    final Integer ydz = dailyTrainTicket.getYdz() - 1;
                    if (ydz < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_NOT_ENOUGH_ERROR);
                    }
                    dailyTrainTicket.setYdz(ydz);
                }case EDZ -> {
                    final Integer edz = dailyTrainTicket.getEdz() - 1;
                    if (edz < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_NOT_ENOUGH_ERROR);
                    }
                    dailyTrainTicket.setEdz(edz);
                }case RW -> {
                    final Integer rw = dailyTrainTicket.getRw() - 1;
                    if (rw < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_NOT_ENOUGH_ERROR);
                    }
                    dailyTrainTicket.setRw(rw);
                }case YW -> {
                    final Integer yw = dailyTrainTicket.getYw() - 1;
                    if (yw < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_NOT_ENOUGH_ERROR);
                    }
                    dailyTrainTicket.setYw(yw);
                }
            }
        }
//        选座
//          一个车厢一个车厢的获取座位数据
//          挑选符合条件的座位，如果这个车厢不满足，则进入下一个车厢（多个选座应该在同一个车厢中）
//        选中座位后事务处理：
//          座位表修改售卖情况sell
//          余票详情表修改余票
//          为会员增加购票记录
//          更新确认订单为成功
    }


}
