package com.nene.controller;

import com.nene.domain.ResponseResult;
import com.nene.domain.dto.UserRegisterDto;
import com.nene.domain.dto.UserUpdateDto;
import com.nene.service.UserService;
import com.nene.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description 用户数据功能控制接口
 * @Author Protip
 * @Date 2023/1/13 17:48
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public ResponseResult getUserInfo(){
        return userService.getUserInfo(ThreadLocalUtil.getUserId());
    }

    @PutMapping("/info/update")
    public ResponseResult updateUserInfo(@RequestBody UserUpdateDto dto){
        return userService.updateUserInfo(dto);
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserRegisterDto dto){
        return userService.register(dto);
    }
}
