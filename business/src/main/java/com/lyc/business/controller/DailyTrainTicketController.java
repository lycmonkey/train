package com.lyc.business.controller;

import com.lyc.business.req.DailyTrainTicketQueryReq;
import com.lyc.business.req.DailyTrainTicketSaveReq;
import com.lyc.business.resp.DailyTrainTicketQueryResp;
import com.lyc.business.service.impl.DailyTrainTicketService;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/daily-train-ticket")
public class DailyTrainTicketController {

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryList(@Valid DailyTrainTicketQueryReq req) {
        PageResp<DailyTrainTicketQueryResp> list = dailyTrainTicketService.queryList(req);
        return new CommonResp<>(list);
    }
}
