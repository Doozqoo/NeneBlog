package com.nene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nene.domain.ResponseResult;
import com.nene.domain.entity.User;

/**
 * @author zhish
 * @description 针对表【sys_user】的数据库操作Service
 * @createDate 2023-01-02 13:45:40
 */
public interface UserService extends IService<User> {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    ResponseResult getUserInfo(Long userId);
}
