package com.lyc.business.service;

import com.lyc.business.req.TrainQueryReq;
import com.lyc.business.req.TrainSaveReq;
import com.lyc.business.resp.TrainQueryResp;
import com.lyc.common.resp.PageResp;

import java.util.List;

/**
 * @author lyc
 */
public interface TrainService {
    void save(TrainSaveReq req);

    PageResp<TrainQueryResp> queryList(TrainQueryReq req);

    void delete(Long id);

    List<TrainQueryResp> queryAll();

    void genSeat(String trainCode);
}
