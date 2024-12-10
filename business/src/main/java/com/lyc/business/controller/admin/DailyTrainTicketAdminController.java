package com.lyc.business.controller.admin;

import com.lyc.business.service.impl.DailyTrainTicketService;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.resp.PageResp;
import com.lyc.business.req.DailyTrainTicketQueryReq;
import com.lyc.business.req.DailyTrainTicketSaveReq;
import com.lyc.business.resp.DailyTrainTicketQueryResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin/daily-train-ticket")
public class DailyTrainTicketAdminController {

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainTicketSaveReq req) {
        dailyTrainTicketService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryList(@Valid DailyTrainTicketQueryReq req) {
        PageResp<DailyTrainTicketQueryResp> list = dailyTrainTicketService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainTicketService.delete(id);
        return new CommonResp<>();
    }
/*

    @GetMapping("/gen-daily-ticket")
    public CommonResp genDailyTicket(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            String trainCode) {
        dailyTrainTicketService.genDailyTicket(date, trainCode);
        return new CommonResp();
    }
*/

}
