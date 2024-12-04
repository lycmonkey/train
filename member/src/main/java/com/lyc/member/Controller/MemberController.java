package com.lyc.member.Controller;

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
    public long register(String mobile) {
        return memberService.register(mobile);
    }






    @GetMapping("/count")
    public int count() {
        return memberService.count();
    }

}
