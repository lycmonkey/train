package com.lyc.member.Controller;

import com.lyc.common.resp.CommonResp;
import com.lyc.member.req.PassengerSaveAndUpdateReq;
import com.lyc.member.service.PassengerService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lyc
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {


    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody PassengerSaveAndUpdateReq passengerReq) {
        passengerService.save(passengerReq);
        return new CommonResp();
    }

}
