package com.lyc.business.controller.admin;

import com.lyc.business.service.impl.DailyTrainCarriageService;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.resp.PageResp;
import com.lyc.business.req.DailyTrainCarriageQueryReq;
import com.lyc.business.req.DailyTrainCarriageSaveReq;
import com.lyc.business.resp.DailyTrainCarriageQueryResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin/daily-train-carriage")
public class DailyTrainCarriageAdminController {

    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainCarriageSaveReq req) {
        dailyTrainCarriageService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainCarriageQueryResp>> queryList(@Valid DailyTrainCarriageQueryReq req) {
        PageResp<DailyTrainCarriageQueryResp> list = dailyTrainCarriageService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainCarriageService.delete(id);
        return new CommonResp<>();
    }

    @GetMapping("/gen-daily/")
    CommonResp genDaily(
            @PathVariable @DateTimeFormat(pattern = "yy-MM-dd") Date date,
            String trainCode) {
        dailyTrainCarriageService.genDaily(date, trainCode);

        return new CommonResp();
    }

}
