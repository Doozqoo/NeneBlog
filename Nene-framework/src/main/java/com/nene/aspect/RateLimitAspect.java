package com.nene.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.nene.annotation.RateLimit;
import com.nene.exception.RateLimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Order(1)
public class RateLimitAspect {

    // 使用一个ConcurrentHashMap来存储每个方法对应的令牌桶
    private final Map<String, RateLimiter> buckets = new ConcurrentHashMap<>();

    // 定义一个切点，匹配被@RateLimit注解标记的方法
    @Pointcut("@annotation(com.nene.annotation.RateLimit)")
    public void rateLimitPointcut() {
    }

    // 定义一个前置通知，在切点之前执行
    @Before("rateLimitPointcut() && @annotation(rateLimit)")
    public void checkRateLimit(JoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        // 获取ip
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = request.getRemoteHost();
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法名称
        String methodName = signature.getMethod().getName();
        // 根据方法名称和ip构造一个唯一的键
        String key = ip + "-" + methodName;
        // 如果buckets中没有对应的令牌桶，就创建一个新的令牌桶并放入buckets中
        buckets.putIfAbsent(key, RateLimiter.create(rateLimit.refillRate()));
        // 获取对应的令牌桶
        RateLimiter rateLimiter = buckets.get(key);
        // 尝试从令牌桶中消耗一个令牌，如果成功就继续执行方法，如果失败就抛出异常并返回响应状态码
        if (!rateLimiter.tryAcquire()) {
            throw new RateLimitException(rateLimit.responseStatus());
        }
    }
}
