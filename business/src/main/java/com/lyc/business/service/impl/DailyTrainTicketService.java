package com.lyc.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.business.domain.DailyTrain;
import com.lyc.business.domain.TrainStation;
import com.lyc.business.enums.SeatTypeEnum;
import com.lyc.business.enums.TrainTypeEnum;
import com.lyc.common.resp.PageResp;
import com.lyc.common.util.SnowUtil;
import com.lyc.business.domain.DailyTrainTicket;
import com.lyc.business.domain.DailyTrainTicketExample;
import com.lyc.business.mapper.DailyTrainTicketMapper;
import com.lyc.business.req.DailyTrainTicketQueryReq;
import com.lyc.business.req.DailyTrainTicketSaveReq;
import com.lyc.business.resp.DailyTrainTicketQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class DailyTrainTicketService {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainTicketService.class);

    @Resource
    private DailyTrainTicketMapper dailyTrainTicketMapper;
    @Resource
    private TrainStationService trainStationService;
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;


    public void save(DailyTrainTicketSaveReq req) {
        DateTime now = DateTime.now();
        DailyTrainTicket dailyTrainTicket = BeanUtil.copyProperties(req, DailyTrainTicket.class);
        if (ObjectUtil.isNull(dailyTrainTicket.getId())) {
            dailyTrainTicket.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainTicket.setCreateTime(now);
            dailyTrainTicket.setUpdateTime(now);
            dailyTrainTicketMapper.insert(dailyTrainTicket);
        } else {
            dailyTrainTicket.setUpdateTime(now);
            dailyTrainTicketMapper.updateByPrimaryKey(dailyTrainTicket);
        }
    }

    public PageResp<DailyTrainTicketQueryResp> queryList(DailyTrainTicketQueryReq req) {
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.setOrderByClause("id desc");
        final DailyTrainTicketExample.Criteria criteria = dailyTrainTicketExample.createCriteria();
        if (ObjectUtil.isNotNull(req.getDate())) {
            criteria.andDateEqualTo(req.getDate());
        }
        if (StrUtil.isNotBlank(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }
        if (StrUtil.isNotBlank(req.getStart())) {
            criteria.andStartEqualTo(req.getStart());
        }
        if (StrUtil.isNotBlank(req.getEnd())) {
            criteria.andEndEqualTo(req.getEnd());
        }
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainTicket> dailyTrainTicketList = dailyTrainTicketMapper.selectByExample(dailyTrainTicketExample);

        PageInfo<DailyTrainTicket> pageInfo = new PageInfo<>(dailyTrainTicketList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainTicketQueryResp> list = BeanUtil.copyToList(dailyTrainTicketList, DailyTrainTicketQueryResp.class);

        PageResp<DailyTrainTicketQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainTicketMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void genDailyTicket(Date date, String trainCode, DailyTrain dailyTrain) {
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.createCriteria().andDateEqualTo(date).andTrainCodeEqualTo(trainCode);
        dailyTrainTicketMapper.deleteByExample(dailyTrainTicketExample);
        final String trainType = dailyTrain.getType();

        final Date now = new Date();
        final List<TrainStation> trainStations = trainStationService.selectByTrainCode(trainCode);
        for (int i = 0; i < trainStations.size(); i++) {
            final TrainStation trainStationStart = trainStations.get(i);
            BigDecimal sumKm = BigDecimal.ZERO;
            for (int j = i + 1; j < trainStations.size(); j++) {
                final TrainStation trainStationEnd = trainStations.get(j);
                final BigDecimal km = trainStationEnd.getKm();
                sumKm = sumKm.add(km);
                final BigDecimal priceRate = EnumUtil.getFieldBy(TrainTypeEnum::getPriceRate, TrainTypeEnum::getCode, trainType);
                BigDecimal ydzPrice = sumKm.multiply(priceRate).multiply(SeatTypeEnum.YDZ.getPrice()).setScale(2, RoundingMode.HALF_UP);
                BigDecimal edzPrice = sumKm.multiply(priceRate).multiply(SeatTypeEnum.EDZ.getPrice()).setScale(2, RoundingMode.HALF_UP);
                BigDecimal rwPrice = sumKm.multiply(priceRate).multiply(SeatTypeEnum.YW.getPrice()).setScale(2, RoundingMode.HALF_UP);
                BigDecimal ywPrice = sumKm.multiply(priceRate).multiply(SeatTypeEnum.RW.getPrice()).setScale(2, RoundingMode.HALF_UP);
                DailyTrainTicket dailyTrainTicket = new DailyTrainTicket();
                dailyTrainTicket.setId(SnowUtil.getSnowflakeNextId());
                dailyTrainTicket.setDate(date);
                dailyTrainTicket.setTrainCode(trainCode);
                dailyTrainTicket.setStart(trainStationStart.getName());
                dailyTrainTicket.setStartPinyin(trainStationStart.getNamePinyin());
                dailyTrainTicket.setStartTime(trainStationStart.getOutTime());
                dailyTrainTicket.setStartIndex(trainStationStart.getIndex());
                dailyTrainTicket.setEnd(trainStationEnd.getName());
                dailyTrainTicket.setEndPinyin(trainStationEnd.getNamePinyin());
                dailyTrainTicket.setEndTime(trainStationEnd.getInTime());
                dailyTrainTicket.setEndIndex(trainStationEnd.getIndex());
                final int ydz = dailyTrainSeatService.count(date, trainCode, SeatTypeEnum.YDZ.getCode());
                final int edz = dailyTrainSeatService.count(date, trainCode, SeatTypeEnum.EDZ.getCode());
                final int rw = dailyTrainSeatService.count(date, trainCode, SeatTypeEnum.RW.getCode());
                final int yw = dailyTrainSeatService.count(date, trainCode, SeatTypeEnum.YW.getCode());
                dailyTrainTicket.setYdz(ydz);
                dailyTrainTicket.setYdzPrice(ydzPrice);
                dailyTrainTicket.setEdz(edz);
                dailyTrainTicket.setEdzPrice(edzPrice);
                dailyTrainTicket.setRw(rw);
                dailyTrainTicket.setRwPrice(rwPrice);
                dailyTrainTicket.setYw(yw);
                dailyTrainTicket.setYwPrice(ywPrice);
                dailyTrainTicket.setCreateTime(now);
                dailyTrainTicket.setUpdateTime(now);
                dailyTrainTicketMapper.insert(dailyTrainTicket);


            }
        }
    }

    public DailyTrainTicket selectByUnique(Date date, String trainCode, String start, String end) {
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode)
                .andStartEqualTo(start)
                .andEndEqualTo(end);
        final List<DailyTrainTicket> dailyTrainTickets = dailyTrainTicketMapper.selectByExample(dailyTrainTicketExample);
        return CollUtil.isEmpty(dailyTrainTickets) ? null : dailyTrainTickets.get(0);
    }




}
