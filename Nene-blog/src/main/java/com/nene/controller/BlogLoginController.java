package com.nene.controller;

import com.nene.domain.ResponseResult;
import com.nene.domain.dto.UserDto;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.exception.CustomServiceException;
import com.nene.service.BlogLoginService;
import com.nene.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseResult login(@RequestBody UserDto userDto) {
        if (userDto.getAccount() == null || userDto.getPassword() == null) {
            throw new CustomServiceException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        return blogLoginService.login(userDto);
    }

    @GetMapping("/logout")
    public ResponseResult logout() {
        return blogLoginService.logout(ThreadLocalUtil.getUser());
    }
}
