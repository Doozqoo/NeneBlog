package com.nene.handler;

import com.nene.domain.ResponseResult;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.utils.JacksonUtil;
import com.nene.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AccessDeniedHandlerImpl
 * @Description 授权异常处理类
 * @Author Protip
 * @Date 2023/1/10 15:15
 * @Version 1.0
 */
@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info(e.toString());
        ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        WebUtil.renderString(response, JacksonUtil.writeValueAsString(responseResult));
    }
}
