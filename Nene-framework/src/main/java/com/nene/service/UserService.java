package com.nene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nene.domain.ResponseResult;
import com.nene.domain.dto.UserRegisterDto;
import com.nene.domain.dto.UserUpdateDto;
import com.nene.domain.entity.User;

/**
 * @author zhish
 * @description 针对表【sys_user】的数据库操作Service
 * @createDate 2023-01-02 13:45:40
 */
public interface UserService extends IService<User> {

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    ResponseResult getUserInfo(Long userId);

    /**
     * 更新用户信息
     *
     * @param dto 用户信息数据
     * @return
     */
    ResponseResult updateUserInfo(UserUpdateDto dto);

    /**
     * 用户注册
     *
     * @param dto 用户注册基本数据
     * @return
     */
    ResponseResult register(UserRegisterDto dto);
}
