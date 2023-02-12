package com.nene.controller;

import com.nene.annotation.ApiLog;
import com.nene.domain.ResponseResult;
import com.nene.domain.dto.UserLoginDto;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.exception.CustomServiceException;
import com.nene.service.AuthorLoginService;
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
@RequestMapping("/admin")
public class AuthorLoginController {

    @Autowired
    private AuthorLoginService authorLoginService;

    @ApiLog(name = "用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserLoginDto userLoginDto) {
        if (userLoginDto.getAccount() == null || userLoginDto.getPassword() == null) {
            throw new CustomServiceException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        return authorLoginService.login(userLoginDto);
    }
}
