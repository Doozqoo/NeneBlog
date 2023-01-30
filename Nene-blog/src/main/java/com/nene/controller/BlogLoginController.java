package com.nene.controller;

import com.nene.domain.ResponseResult;
import com.nene.domain.dto.BlogUserLoginDto;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.exception.CustomServiceException;
import com.nene.service.BlogLoginService;
import com.nene.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName BlogLoginController
 * @Description 博客登录控制类
 * @Author Protip
 * @Date 2023/1/7 17:58
 * @Version 1.0
 */
@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody BlogUserLoginDto blogUserLoginDto) {
        if (blogUserLoginDto.getAccount() == null || blogUserLoginDto.getPassword() == null) {
            throw new CustomServiceException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        return blogLoginService.login(blogUserLoginDto);
    }

    @GetMapping("/logout")
    public ResponseResult logout() {
        return blogLoginService.logout(ThreadLocalUtil.getUser());
    }
}
