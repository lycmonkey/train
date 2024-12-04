package com.lyc.member.req;

/**
 * @author lyc
 */
public class MemberRegisterReq {

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
