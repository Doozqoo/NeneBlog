package com.nene.handler;

import com.nene.domain.ResponseResult;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.utils.JacksonUtil;
import com.nene.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthenticationEntryPointImpl
 * @Description 认证异常处理类
 * @Author Protip
 * @Date 2023/1/10 15:17
 * @Version 1.0
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        log.info(e.toString());
        ResponseResult responseResult;
        if (e instanceof BadCredentialsException) {
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR, e.getMessage());
        } else if (e instanceof InsufficientAuthenticationException) {
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else {
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        WebUtil.renderString(response, JacksonUtil.writeValueAsString(responseResult));
    }
}
