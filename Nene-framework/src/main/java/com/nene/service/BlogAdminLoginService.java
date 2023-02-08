package com.nene.service;

import com.nene.domain.ResponseResult;
import com.nene.domain.dto.UserLoginDto;

/**
 * @ClassName BlogAdminLoginService
 * @Description 博客管理平台登录服务接口
 * @Author Protip
 * @Date 2023/2/6 15:28
 * @Version 1.0
 */
public interface BlogAdminLoginService {

    /**
     * 管理端用户登录
     * @param userLoginDto 登录用户信息
     * @return
     */
    ResponseResult login(UserLoginDto userLoginDto);
}
