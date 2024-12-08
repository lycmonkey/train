package com.lyc.business.controller.admin;

import com.lyc.business.service.TrainService;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.resp.PageResp;
import com.lyc.business.req.TrainQueryReq;
import com.lyc.business.req.TrainSaveReq;
import com.lyc.business.resp.TrainQueryResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train")
public class TrainAdminController {

    @Resource
    private TrainService trainService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TrainSaveReq req) {
        trainService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<TrainQueryResp>> queryList(@Valid TrainQueryReq req) {
        PageResp<TrainQueryResp> list = trainService.queryList(req);
        return new CommonResp<>(list);
    }

    @GetMapping("/query-all")
    public CommonResp<List<TrainQueryResp>> queryAll() {
        List<TrainQueryResp> list = trainService.queryAll();
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        trainService.delete(id);
        return new CommonResp<>();
    }

    @GetMapping("/gen-seat/{trainCode}")
    public CommonResp genSeat(@PathVariable String trainCode) {
        trainService.genSeat(trainCode);
        return new CommonResp();
    }

}
