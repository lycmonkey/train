package com.lyc.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.lyc.common.context.LoginMemberContext;
import com.lyc.common.util.SnowUtil;
import com.lyc.member.domain.Passenger;
import com.lyc.member.domain.PassengerExample;
import com.lyc.member.mapper.PassengerMapper;
import com.lyc.member.req.PassengerQueryReq;
import com.lyc.member.req.PassengerSaveAndUpdateReq;
import com.lyc.member.resp.PassengerQueryResp;
import com.lyc.member.service.PassengerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyc
 */
@Service
public class PassengerServiceImpl implements PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    @Override
    public void save(PassengerSaveAndUpdateReq passengerReq) {
        final Passenger passenger = BeanUtil.copyProperties(passengerReq, Passenger.class);
        final DateTime now = DateTime.now();
        passenger.setMemberId(LoginMemberContext.getMemberId());
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }

    @Override
    public List<PassengerQueryResp> queryList(PassengerQueryReq passengerQueryReq) {
        PassengerExample example = new PassengerExample();
        final PassengerExample.Criteria criteria = example.createCriteria();
        final Long memberId = passengerQueryReq.getMemberId();
        if (ObjectUtil.isNotEmpty(memberId)) {
            criteria.andMemberIdEqualTo(memberId);
        }

        final List<Passenger> passengers = passengerMapper.selectByExample(example);
        return BeanUtil.copyToList(passengers, PassengerQueryResp.class);
    }
}
