package com.nene.exception;

import com.nene.enums.AppHttpCodeEnum;

/**
 * @ClassName BusinessException
 * @Description 业务异常
 * @Author Protip
 * @Date 2023/1/10 16:08
 * @Version 1.0
 */
public class CustomServiceException extends RuntimeException {

    private AppHttpCodeEnum appHttpCodeEnum;

    public CustomServiceException(AppHttpCodeEnum appHttpCodeEnum) {
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public AppHttpCodeEnum getAppHttpCodeEnum() {
        return appHttpCodeEnum;
    }

    @Override
    public String toString() {
        return "CustomServiceException{" +
                "appHttpCodeEnum=" + appHttpCodeEnum.getMsg() +
                '}';
    }
}
