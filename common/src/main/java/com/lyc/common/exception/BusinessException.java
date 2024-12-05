package com.lyc.common.exception;

/**
 * @author lyc
 */
public class BusinessException extends RuntimeException{
    private BusinessExceptionEnum businessExceptionEnum;

    public BusinessException(BusinessExceptionEnum businessExceptionEnum) {
        this.businessExceptionEnum = businessExceptionEnum;
    }

    public BusinessExceptionEnum getBusinessExceptionEnum() {
        return businessExceptionEnum;
    }

    public void setBusinessExceptionEnum(BusinessExceptionEnum businessExceptionEnum) {
        this.businessExceptionEnum = businessExceptionEnum;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
