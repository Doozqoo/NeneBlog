package com.nene.controller;

import com.nene.domain.ResponseResult;
import com.nene.domain.dto.UserLoginDto;
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
    public ResponseResult login(@RequestBody UserLoginDto userLoginDto) {
        if (userLoginDto.getAccount() == null || userLoginDto.getPassword() == null) {
            throw new CustomServiceException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        return blogLoginService.login(userLoginDto);
    }

    @GetMapping("/logout")
    public ResponseResult logout() {
        return blogLoginService.logout(ThreadLocalUtil.getUser());
    }
}
