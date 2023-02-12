package com.nene.config;

import com.nene.config.properties.CustomPassRules;
import com.nene.filter.JwtAuthorizeFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName SecurityConfig
 * @Description Security配置类
 * @Author Protip
 * @Date 2023/1/5 16:57
 * @Version 1.0
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthorizeFilter jwtAuthorizeFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final CustomPassRules customPassRules;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 允许匿名访问的接口
        String[] urls = customPassRules.getExcludedUrls().toArray(new String[0]);

        http
                // 允许跨域访问
                .cors(Customizer.withDefaults())
                // 关闭默认注销接口
                .logout().disable()
                // 关闭csrf(跨站请求伪造)
                .csrf().disable()
                // 不创建session(会话)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 添加过滤器
                .addFilterBefore(jwtAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                // 请求权限配置
                .authorizeRequests()
                // 指定请求路径的放行策略
                .antMatchers(urls).permitAll()
                .anyRequest().authenticated().and()
                // 授权异常处理
                .exceptionHandling()
                // json提示用户没有登录不需要用户跳转到登录页面去
                .authenticationEntryPoint(authenticationEntryPoint)
                // 权限拦截器，提示用户没有当前权限
                .accessDeniedHandler(accessDeniedHandler);
    }
}

