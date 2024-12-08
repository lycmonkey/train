package com.lyc.common.exception;

/**
 * @author lyc
 */
public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("手机号已注册"),
    MEMBER_CODE_NOT_EXIST("验证码错误"),
    BUSINESS_STATION_NAME_UNIQUE("车站名字不能重复"),
    MEMBER_MOBILE_NOT_EXIST("手机号不存在")
    ;


    private String msg;

    @Override
    public String toString() {
        return "BusinessExceptionEnum{" +
                "msg='" + msg + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    BusinessExceptionEnum() {
    }

    BusinessExceptionEnum(String msg) {
        this.msg = msg;
    }
}
