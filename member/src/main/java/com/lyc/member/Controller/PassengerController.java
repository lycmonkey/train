package com.lyc.member.Controller;

import com.lyc.common.context.LoginMemberContext;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.resp.PageResp;
import com.lyc.member.req.PassengerQueryReq;
import com.lyc.member.req.PassengerSaveAndUpdateReq;
import com.lyc.member.resp.PassengerQueryResp;
import com.lyc.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lyc
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {


    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Valid PassengerSaveAndUpdateReq passengerReq) {
        passengerService.save(passengerReq);
        return new CommonResp();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<PassengerQueryResp>> queryList(PassengerQueryReq passengerQueryReq) {
        passengerQueryReq.setMemberId(LoginMemberContext.getMemberId());
        return new CommonResp<>(passengerService.queryList(passengerQueryReq));

    }

}
