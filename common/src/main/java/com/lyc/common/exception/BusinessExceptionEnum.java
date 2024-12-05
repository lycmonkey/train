package com.lyc.common.exception;

/**
 * @author lyc
 */
public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("手机号已注册");


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
