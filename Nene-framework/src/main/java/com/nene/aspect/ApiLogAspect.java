package com.nene.aspect;

import com.nene.annotation.ApiLog;
import com.nene.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ApiLogAspect
 * @Description 日志切面类
 * @Author Protip
 * @Date 2023/2/1 10:38
 * @Version 1.0
 */
@Component
@Aspect
@Slf4j
public class ApiLogAspect {

    @Pointcut(value = "@annotation(com.nene.annotation.ApiLog)")
    public void point() {
    }

    @Around(value = "point()")
    public Object consolePrintLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            before(joinPoint);
            result = joinPoint.proceed();
            after(result);
        } finally {
            log.info("++++++++++++++++++++++++++++++end++++++++++++++++++++++++++++++" + System.lineSeparator());
        }
        return result;
    }

    private void before(ProceedingJoinPoint joinPoint) {

        // 获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取方法上的注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ApiLog apiLog = signature.getMethod().getAnnotation(ApiLog.class);


        log.info("++++++++++++++++++++++++++++++start++++++++++++++++++++++++++++++");
        log.info("url          : {}", request.getRequestURL());
        log.info("title        : {}", apiLog.name());
        log.info("http method  : {}", request.getMethod());
        log.info("class method : {}.{}", signature.getDeclaringTypeName(), signature.getName());
        log.info("ip           : {}", request.getRemoteHost());
        log.info("request args : {}", JacksonUtil.writeValueAsString(joinPoint.getArgs()));
    }

    private void after(Object result) {
        log.info("response     : {}", JacksonUtil.writeValueAsString(result));
    }

}
