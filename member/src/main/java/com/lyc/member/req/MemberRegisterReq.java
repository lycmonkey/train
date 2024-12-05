package com.lyc.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @author lyc
 */
public class MemberRegisterReq {

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
//    @NotBlank(message = "手机号不能为空")
    private String mobile;

    public MemberRegisterReq() {
    }

    public MemberRegisterReq(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public String toString() {
        return "MemberRegisterReq{" +
                "mobile='" + mobile + '\'' +
                '}';
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
