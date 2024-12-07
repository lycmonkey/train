package com.lyc.member.service;

import com.lyc.member.req.PassengerQueryReq;
import com.lyc.member.req.PassengerSaveAndUpdateReq;
import com.lyc.member.resp.PassengerQueryResp;

import java.util.List;

/**
 * @author lyc
 */
public interface PassengerService {

    void save(PassengerSaveAndUpdateReq passengerReq);

    List<PassengerQueryResp> queryList(PassengerQueryReq passengerQueryReq);
}
