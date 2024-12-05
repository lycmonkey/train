package com.lyc.member.Controller;

import com.lyc.common.resp.CommonResp;
import com.lyc.member.req.MemberLoginReq;
import com.lyc.member.req.MemberRegisterReq;
import com.lyc.member.resp.MemberLoginResp;
import com.lyc.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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
    public CommonResp<Long> register(@Valid MemberRegisterReq memberRegisterReq) {
        return memberService.register(memberRegisterReq);
    }

    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid MemberRegisterReq memberRegisterReq) {
        memberService.sendCode(memberRegisterReq);
        return new CommonResp<>();
    }

    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid MemberLoginReq memberLoginReq) {
        return memberService.login(memberLoginReq);
    }








    @GetMapping("/count")
    public CommonResp<Integer> count() {
        return memberService.count();
    }

}
