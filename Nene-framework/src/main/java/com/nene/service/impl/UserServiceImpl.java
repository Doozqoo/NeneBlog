package com.nene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.domain.entity.User;
import com.nene.mapper.UserMapper;
import com.nene.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zhish
 * @description 针对表【sys_user】的数据库操作Service实现
 * @createDate 2023-01-02 13:45:40
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

}




