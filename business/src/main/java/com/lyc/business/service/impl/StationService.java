package com.lyc.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.common.exception.BusinessException;
import com.lyc.common.exception.BusinessExceptionEnum;
import com.lyc.common.resp.PageResp;
import com.lyc.common.util.SnowUtil;
import com.lyc.business.domain.Station;
import com.lyc.business.domain.StationExample;
import com.lyc.business.mapper.StationMapper;
import com.lyc.business.req.StationQueryReq;
import com.lyc.business.req.StationSaveReq;
import com.lyc.business.resp.StationQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    private static final Logger LOG = LoggerFactory.getLogger(StationService.class);

    @Resource
    private StationMapper stationMapper;

    public void save(StationSaveReq req) {
        DateTime now = DateTime.now();
        Station station = BeanUtil.copyProperties(req, Station.class);
        if (ObjectUtil.isNull(station.getId())) {
            StationExample stationExample = new StationExample();
            StationExample.Criteria criteria = stationExample.createCriteria();
            criteria.andNameEqualTo(station.getName());
            final List<Station> stations = stationMapper.selectByExample(stationExample);
            if (ObjectUtil.isNull(stations.get(0))) {
                station.setId(SnowUtil.getSnowflakeNextId());
                station.setCreateTime(now);
                station.setUpdateTime(now);
                stationMapper.insert(station);
            }
            throw new BusinessException(BusinessExceptionEnum.BUSINESS_STATION_NAME_UNIQUE);
        } else {
            station.setUpdateTime(now);
            stationMapper.updateByPrimaryKey(station);
        }
    }

    public PageResp<StationQueryResp> queryList(StationQueryReq req) {
        StationExample stationExample = new StationExample();
        stationExample.setOrderByClause("id desc");
        StationExample.Criteria criteria = stationExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Station> stationList = stationMapper.selectByExample(stationExample);

        PageInfo<Station> pageInfo = new PageInfo<>(stationList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<StationQueryResp> list = BeanUtil.copyToList(stationList, StationQueryResp.class);

        PageResp<StationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        stationMapper.deleteByPrimaryKey(id);
    }

    public List<StationQueryResp> queryAll() {
        StationExample stationExample = new StationExample();
        final List<Station> stations = stationMapper.selectByExample(stationExample);
        return BeanUtil.copyToList(stations, StationQueryResp.class);
    }
}
