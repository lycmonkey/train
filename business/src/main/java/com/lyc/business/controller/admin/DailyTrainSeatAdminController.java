package com.lyc.business.controller.admin;

import com.lyc.business.service.impl.DailyTrainSeatService;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.resp.PageResp;
import com.lyc.business.req.DailyTrainSeatQueryReq;
import com.lyc.business.req.DailyTrainSeatSaveReq;
import com.lyc.business.resp.DailyTrainSeatQueryResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin/daily-train-seat")
public class DailyTrainSeatAdminController {

    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainSeatSaveReq req) {
        dailyTrainSeatService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainSeatQueryResp>> queryList(@Valid DailyTrainSeatQueryReq req) {
        PageResp<DailyTrainSeatQueryResp> list = dailyTrainSeatService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainSeatService.delete(id);
        return new CommonResp<>();
    }
    @GetMapping("/admin/daily-train-seat/gen-daily/")
    CommonResp genDailyTrainSeat(
            @PathVariable @DateTimeFormat(pattern = "yy-MM-dd") Date date,
            String trainCode) {
        dailyTrainSeatService.genDaily(date, trainCode);
        return new CommonResp();
    }

}
