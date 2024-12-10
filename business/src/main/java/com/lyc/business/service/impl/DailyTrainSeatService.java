package com.lyc.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.business.domain.*;
import com.lyc.common.resp.PageResp;
import com.lyc.common.util.SnowUtil;
import com.lyc.business.mapper.DailyTrainSeatMapper;
import com.lyc.business.req.DailyTrainSeatQueryReq;
import com.lyc.business.req.DailyTrainSeatSaveReq;
import com.lyc.business.resp.DailyTrainSeatQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DailyTrainSeatService {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainSeatService.class);

    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;

    @Resource
    private TrainSeatService trainSeatService;

    @Resource
    private TrainStationService trainStationService;

    public void save(DailyTrainSeatSaveReq req) {
        DateTime now = DateTime.now();
        DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(req, DailyTrainSeat.class);
        if (ObjectUtil.isNull(dailyTrainSeat.getId())) {
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        } else {
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.updateByPrimaryKey(dailyTrainSeat);
        }
    }

    public PageResp<DailyTrainSeatQueryResp> queryList(DailyTrainSeatQueryReq req) {
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.setOrderByClause("id desc");
        DailyTrainSeatExample.Criteria criteria = dailyTrainSeatExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainSeat> dailyTrainSeatList = dailyTrainSeatMapper.selectByExample(dailyTrainSeatExample);

        PageInfo<DailyTrainSeat> pageInfo = new PageInfo<>(dailyTrainSeatList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainSeatQueryResp> list = BeanUtil.copyToList(dailyTrainSeatList, DailyTrainSeatQueryResp.class);

        PageResp<DailyTrainSeatQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainSeatMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void genDaily(Date date, String trainCode) {
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.createCriteria().andDateEqualTo(date).andTrainCodeEqualTo(trainCode);
        dailyTrainSeatMapper.deleteByExample(dailyTrainSeatExample);
        final List<TrainSeat> trainSeats = trainSeatService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(trainSeats)) {
            return;
        }
        final List<TrainStation> trainStations = trainStationService.selectByTrainCode(trainCode);
        final String sell = StrUtil.fillBefore("", '0', trainStations.size() - 1);
        final Date now = new Date();
        final List<DailyTrainSeat> dailyTrainSeats = BeanUtil.copyToList(trainSeats, DailyTrainSeat.class);
        for (DailyTrainSeat dailyTrainSeat : dailyTrainSeats) {
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setDate(date);
            dailyTrainSeat.setSell(sell);
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        }

    }

    public int count(Date date, String trainCode, String seatType) {
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.createCriteria().andDateEqualTo(date).andTrainCodeEqualTo(trainCode).andSeatTypeEqualTo(seatType);
        final int count = (int) dailyTrainSeatMapper.countByExample(dailyTrainSeatExample);
        return count == 0 ? -1 : count;

    }
}
