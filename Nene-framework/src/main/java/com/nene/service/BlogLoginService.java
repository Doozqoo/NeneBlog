package com.nene.service;

import com.nene.domain.ResponseResult;
import com.nene.domain.dto.BlogUserLoginDto;
import com.nene.domain.entity.User;

/**
 * @ClassName BlogLoginService
 * @Description 博客登录服务接口
 * @Author Protip
 * @Date 2023/1/7 18:14
 * @Version 1.0
 */
public interface BlogLoginService {

    /**
     * 博客用户登录
     *
     * @param blogUserLoginDto 用户登录数据
     * @return 登录操作结果
     */
    ResponseResult login(BlogUserLoginDto blogUserLoginDto);

    /**
     * 退出登录
     *
     * @param user 当前登录用户
     * @return 退出登录操作结果
     */
    ResponseResult logout(User user);
}
