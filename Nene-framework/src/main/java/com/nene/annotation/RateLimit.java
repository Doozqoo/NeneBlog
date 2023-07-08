package com.nene.annotation;

import org.springframework.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    // 令牌的生成速率，单位为秒，默认为1
    double refillRate() default 1.0;

    // 超出限制时的响应，默认为HTTP 429 Too Many Requests
    HttpStatus responseStatus() default HttpStatus.TOO_MANY_REQUESTS;
}
