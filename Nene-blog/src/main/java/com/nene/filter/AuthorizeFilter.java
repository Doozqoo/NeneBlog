package com.nene.filter;

import com.nene.cache.RedisCache;
import com.nene.config.properties.CustomPassRules;
import com.nene.constants.RedisConstants;
import com.nene.domain.LoginUser;
import com.nene.domain.ResponseResult;
import com.nene.domain.entity.User;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.utils.JacksonUtil;
import com.nene.utils.JwtUtil;
import com.nene.utils.ThreadLocalUtil;
import com.nene.utils.WebUtil;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName AuthorizeFilter
 * @Description 权限认证过滤类
 * @Author Protip
 * @Date 2023/1/10 11:19
 * @Version 1.0
 */
@Component
public class AuthorizeFilter extends OncePerRequestFilter implements HandlerInterceptor {

    @Autowired
    private CustomPassRules customPassRules;

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {

        // 排除的地址直接放行
        String uri = request.getRequestURI();
        List<String> excludedUrls = customPassRules.getExcludedUrls();
        if (excludedUrls.contains(uri)) {
            chain.doFilter(request, response);
            return;
        }

        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 需要登录获取token
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtil.renderString(response, JacksonUtil.writeValueAsString(responseResult));
            return;
        }

        // 解析token
        Claims claimsBody = JwtUtil.getClaimsBody(token);
        if (claimsBody == null) {
            // token解析失败, 需要重新登录
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtil.renderString(response, JacksonUtil.writeValueAsString(responseResult));
            return;
        }

        // 判断token是否过期
        int result = JwtUtil.verifyToken(claimsBody);
        if (result == 1 || result == 2) {
            // token已过期, 需要重新登录
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtil.renderString(response, JacksonUtil.writeValueAsString(responseResult));
            return;
        }

        // 解析token获取用户id
        String id = claimsBody.get("id").toString();
        // 从redis中获取用户信息
        User user = redisCache.getValue(RedisConstants.BLOG_LOGIN + id, User.class);
        if (user == null) {
            // 已注销, 需要重新登录
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtil.renderString(response, JacksonUtil.writeValueAsString(responseResult));
            return;
        }

        // 将用户信息存入当前线程中
        ThreadLocalUtil.set(user);

        // 将认证后的权限设置放行
        LoginUser principal = new LoginUser(user.getUserName(), user.getPassword());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
