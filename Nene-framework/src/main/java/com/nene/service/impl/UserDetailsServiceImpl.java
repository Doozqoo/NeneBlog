package com.nene.service.impl;

import com.nene.constants.SystemConstants;
import com.nene.domain.LoginUser;
import com.nene.domain.entity.User;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.exception.CustomServiceException;
import com.nene.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description 用户登录数据校验服务实现类
 * @Author Protip
 * @Date 2023/1/9 11:31
 * @Version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        User user = userService.lambdaQuery()
                .select(User::getPassword,
                        User::getStatus)
                .eq(User::getEmail, account)
                .or()
                .eq(User::getPhoneNumber, account)
                .or()
                .eq(User::getUserName, account)
                .one();

        if (user == null) {
            throw new CustomServiceException(AppHttpCodeEnum.USER_NOT_EXIST);
        } else if (SystemConstants.USER_STATUS_CLOSE.equals(user.getStatus())) {
            throw new CustomServiceException(AppHttpCodeEnum.USER_STATUS_CLOSED);
        }

        return new LoginUser(account, user.getPassword());
    }
}
