package com.lyc.batch.feign;

import com.lyc.common.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

/**
 * @author lyc
 * 调用Business模块
 */
//@FeignClient("business")
@FeignClient(name = "business", url = "http://127.0.0.1:8002/business")
public interface BusinessFeign {

    @GetMapping("/hello")
    String hello();

    @GetMapping("/admin/daily-train/gen-daily/{date}")
    CommonResp genDaily(@PathVariable @DateTimeFormat(pattern = "yy-MM-dd") Date date);

    /*@GetMapping("/admin/daily-train-station/gen-daily/")
    CommonResp genDailyStation(
            @PathVariable @DateTimeFormat(pattern = "yy-MM-dd") Date date,
            String trainCode);

    @GetMapping("/admin/daily-train-carriage/gen-daily/")
    CommonResp genDailyTrainCarriage(
            @PathVariable @DateTimeFormat(pattern = "yy-MM-dd") Date date,
            String trainCode);

    @GetMapping("/admin/daily-train-seat/gen-daily/")
    CommonResp genDailyTrainSeat(
            @PathVariable @DateTimeFormat(pattern = "yy-MM-dd") Date date,
            String trainCode);*/
}
