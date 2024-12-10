package com.lyc.business.controller.admin;

import com.lyc.business.service.impl.DailyTrainStationService;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.resp.PageResp;
import com.lyc.business.req.DailyTrainStationQueryReq;
import com.lyc.business.req.DailyTrainStationSaveReq;
import com.lyc.business.resp.DailyTrainStationQueryResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin/daily-train-station")
public class DailyTrainStationAdminController {

    @Resource
    private DailyTrainStationService dailyTrainStationService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainStationSaveReq req) {
        dailyTrainStationService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainStationQueryResp>> queryList(@Valid DailyTrainStationQueryReq req) {
        PageResp<DailyTrainStationQueryResp> list = dailyTrainStationService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainStationService.delete(id);
        return new CommonResp<>();
    }

    @GetMapping("/gen-daily/")
    public CommonResp genDailyStation(
            @PathVariable @DateTimeFormat(pattern = "yy-MM-dd") Date date,
            String trainCode){

        dailyTrainStationService.genDaily(date, trainCode);

        return new CommonResp();
    }



}
