package com.lyc.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.common.context.LoginMemberContext;
import com.lyc.common.resp.PageResp;
import com.lyc.common.util.SnowUtil;
import com.lyc.member.domain.Passenger;
import com.lyc.member.domain.PassengerExample;
import com.lyc.member.mapper.PassengerMapper;
import com.lyc.member.req.PassengerQueryReq;
import com.lyc.member.req.PassengerSaveAndUpdateReq;
import com.lyc.member.resp.PassengerQueryResp;
import com.lyc.member.service.PassengerService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyc
 */
@Service
public class PassengerServiceImpl implements PassengerService {

    private static final Logger LOG = LoggerFactory.getLogger(PassengerServiceImpl.class);

    @Resource
    private PassengerMapper passengerMapper;

    @Override
    public void save(PassengerSaveAndUpdateReq passengerReq) {
        LOG.info("开始新增乘车人");
        final Passenger passenger = BeanUtil.copyProperties(passengerReq, Passenger.class);
        final DateTime now = DateTime.now();
        passenger.setMemberId(LoginMemberContext.getMemberId());
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        LOG.info("乘车人信息：{}", passenger);
        passengerMapper.insert(passenger);
        LOG.info("新增成功");
    }

    @Override
    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq passengerQueryReq) {
        LOG.info("开始分页查询");
        PassengerExample example = new PassengerExample();
        final PassengerExample.Criteria criteria = example.createCriteria();
        final Long memberId = passengerQueryReq.getMemberId();
        if (ObjectUtil.isNotEmpty(memberId)) {
            criteria.andMemberIdEqualTo(memberId);
        }
        LOG.info("pageNum:{}", passengerQueryReq.getPage());
        LOG.info("pageSize:{}", passengerQueryReq.getSize());
        PageHelper.startPage(passengerQueryReq.getPage(), passengerQueryReq.getSize());
        final List<Passenger> passengers = passengerMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(passengers);
        LOG.info("总条数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
        return new PageResp<>(pageInfo.getTotal(), BeanUtil.copyToList(passengers, PassengerQueryResp.class));
    }
}
