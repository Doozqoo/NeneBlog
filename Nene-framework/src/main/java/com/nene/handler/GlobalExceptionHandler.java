package com.nene.handler;

import com.nene.domain.ResponseResult;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.exception.CustomServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName GlobalExceptionHandle
 * @Description 全局异常处理器
 * @Author Protip
 * @Date 2023/1/10 16:14
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomServiceException.class})
    public ResponseResult customExceptionHandle(CustomServiceException e) {
        log.info(e.toString());
        AppHttpCodeEnum appHttpCodeEnum = e.getAppHttpCodeEnum();
        return ResponseResult.errorResult(appHttpCodeEnum);
    }

    @ExceptionHandler({Exception.class})
    public ResponseResult exceptionHandle(Exception e) {
        log.info(e.toString());
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
