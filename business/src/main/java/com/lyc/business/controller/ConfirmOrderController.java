package com.lyc.business.controller;

import com.lyc.business.req.ConfirmOrderDoReq;
import com.lyc.business.req.ConfirmOrderQueryReq;
import com.lyc.business.resp.ConfirmOrderQueryResp;
import com.lyc.business.service.impl.ConfirmOrderService;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/confirm-order")
public class ConfirmOrderController {

    @Resource
    private ConfirmOrderService confirmOrderService;

    @PostMapping("/do")
    public CommonResp<Object> doConfirm(@Valid @RequestBody ConfirmOrderDoReq req) {
        confirmOrderService.doConfirm(req);
        return new CommonResp<>();
    }

}
