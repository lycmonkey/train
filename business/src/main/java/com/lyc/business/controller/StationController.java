package com.lyc.business.controller;

import com.lyc.business.req.StationQueryReq;
import com.lyc.business.req.StationSaveReq;
import com.lyc.business.resp.StationQueryResp;
import com.lyc.business.service.impl.StationService;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController {

    @Resource
    private StationService stationService;



    @GetMapping("/query-list")
    public CommonResp<PageResp<StationQueryResp>> queryList(@Valid StationQueryReq req) {
        PageResp<StationQueryResp> list = stationService.queryList(req);
        return new CommonResp<>(list);
    }

    @GetMapping("/query-all")
    public CommonResp<List<StationQueryResp>> queryAll() {
        List<StationQueryResp> list = stationService.queryAll();
        return new CommonResp<>(list);
    }

}
