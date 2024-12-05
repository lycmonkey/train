package com.lyc.member.req;

import jakarta.validation.constraints.NotBlank;

/**
 * @author lyc
 */
public class MemberRegisterReq {

    @NotBlank(message = "手机号不能为空")
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
