package com.lyc.member.Controller;

import com.lyc.common.resp.CommonResp;
import com.lyc.member.req.MemberRegisterReq;
import com.lyc.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lyc
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @PostMapping("/register")
    public CommonResp<Long> register(MemberRegisterReq memberRegisterReq) {
        return memberService.register(memberRegisterReq);
    }






    @GetMapping("/count")
    public CommonResp<Integer> count() {
        return memberService.count();
    }

}
