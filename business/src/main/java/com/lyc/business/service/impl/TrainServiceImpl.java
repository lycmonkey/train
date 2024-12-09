package com.lyc.business.service.impl;
import java.util.Date;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.business.domain.*;
import com.lyc.business.enums.SeatColEnum;
import com.lyc.business.enums.SeatTypeEnum;
import com.lyc.business.mapper.TrainCarriageMapper;
import com.lyc.business.mapper.TrainSeatMapper;
import com.lyc.business.service.TrainService;
import com.lyc.common.resp.PageResp;
import com.lyc.common.util.SnowUtil;
import com.lyc.business.mapper.TrainMapper;
import com.lyc.business.req.TrainQueryReq;
import com.lyc.business.req.TrainSaveReq;
import com.lyc.business.resp.TrainQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainServiceImpl implements TrainService{

    private static final Logger LOG = LoggerFactory.getLogger(TrainService.class);

    @Resource
    private TrainMapper trainMapper;

    @Resource
    private TrainCarriageService trainCarriageService;

    @Resource
    private TrainSeatService trainSeatService;

    @Resource
    private TrainSeatMapper trainSeatMapper;


    public void save(TrainSaveReq req) {
        DateTime now = DateTime.now();
        Train train = BeanUtil.copyProperties(req, Train.class);
        if (ObjectUtil.isNull(train.getId())) {
            train.setId(SnowUtil.getSnowflakeNextId());
            train.setCreateTime(now);
            train.setUpdateTime(now);
            trainMapper.insert(train);
        } else {
            train.setUpdateTime(now);
            trainMapper.updateByPrimaryKey(train);
        }
    }

    public PageResp<TrainQueryResp> queryList(TrainQueryReq req) {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("id desc");
        TrainExample.Criteria criteria = trainExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Train> trainList = trainMapper.selectByExample(trainExample);

        PageInfo<Train> pageInfo = new PageInfo<>(trainList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainQueryResp> list = BeanUtil.copyToList(trainList, TrainQueryResp.class);

        PageResp<TrainQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        trainMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<TrainQueryResp> queryAll() {
        final List<Train> trains = selectAll();
        return BeanUtil.copyToList(trains, TrainQueryResp.class);
    }

    public List<Train> selectAll() {
        return trainMapper.selectByExample(new TrainExample());
    }


    @Override
    @Transactional
    public void genSeat(String trainCode) {
        trainSeatService.deleteByTrainCode(trainCode);
        final List<TrainCarriage> trainCarriages = trainCarriageService.selectByTrainCode(trainCode);
        final DateTime now = DateTime.now();
        for (TrainCarriage trainCarriage : trainCarriages) {
            final String seatType = trainCarriage.getSeatType();
            final Integer rowCount = trainCarriage.getRowCount();
            final Integer index = trainCarriage.getIndex();
            final List<SeatColEnum> cols = SeatColEnum.getColsByType(seatType);
            int seatIndex = 1;
            for (int i = 1; i <= rowCount; i++) {
                for (SeatColEnum col : cols) {
                    TrainSeat trainSeat = new TrainSeat();
                    trainSeat.setId(SnowUtil.getSnowflakeNextId());
                    trainSeat.setTrainCode(trainCode);
                    trainSeat.setCarriageIndex(index);
//                    把i填充到2位，不够的用'0'补齐
                    trainSeat.setRow(StrUtil.fillBefore(String.valueOf(i), '0', 2));
                    trainSeat.setCol(col.getCode());
                    trainSeat.setSeatType(seatType);
                    trainSeat.setCarriageSeatIndex(seatIndex++);
                    trainSeat.setCreateTime(now);
                    trainSeat.setUpdateTime(now);
                    trainSeatMapper.insert(trainSeat);
                }
            }

        }
    }
}
