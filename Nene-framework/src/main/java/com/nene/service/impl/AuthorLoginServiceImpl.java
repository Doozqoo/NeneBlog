package com.nene.service.impl;

import com.nene.cache.RedisCache;
import com.nene.constants.RedisConstants;
import com.nene.constants.SystemConstants;
import com.nene.domain.ResponseResult;
import com.nene.domain.dto.UserLoginDto;
import com.nene.domain.entity.User;
import com.nene.domain.vo.UserLoginVo;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.exception.CustomServiceException;
import com.nene.service.AuthorLoginService;
import com.nene.service.UserService;
import com.nene.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName BlogAdminLoginServiceImpl
 * @Description 博客管理平台登录服务接口实现类
 * @Author Protip
 * @Date 2023/2/6 15:28
 * @Version 1.0
 */
@Service
public class AuthorLoginServiceImpl implements AuthorLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(UserLoginDto userLoginDto) {

        // 登录授权
        Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginDto.getAccount(), userLoginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate == null) {
            throw new CustomServiceException(AppHttpCodeEnum.LOGIN_ERROR);
        }

        // 查询用户信息
        User user = userService.lambdaQuery()
                .select(User::getId,
                        User::getUserName,
                        User::getNickName,
                        User::getPassword,
                        User::getType,
                        User::getStatus,
                        User::getEmail,
                        User::getPhoneNumber,
                        User::getSex,
                        User::getAvatar,
                        User::getCreateTime
                )
                .eq(User::getEmail, userLoginDto.getAccount())
                .or()
                .eq(User::getPhoneNumber, userLoginDto.getAccount())
                .or()
                .eq(User::getUserName, userLoginDto.getAccount())
                .one();

        if (!SystemConstants.USER_TYPE_AUTHOR.equals(user.getType())) {
            throw new CustomServiceException(AppHttpCodeEnum.NEED_AUTHOR_ACCOUNT);
        }

        // 在redis中缓存用户数据
        String key = RedisConstants.BLOG_AUTHOR_LOGIN + user.getId();
        redisCache.setValue(key, user);
        long expiration = userLoginDto.isRemember() ? RedisConstants.TIMEOUT_REMEMBER : RedisConstants.TIMEOUT_DEFAULT;
        redisCache.expire(key, expiration, TimeUnit.HOURS);

        // 生成token返回
        String token = JwtUtil.getToken(user.getId());
        UserLoginVo userLoginVo = new UserLoginVo(token, null);

        return ResponseResult.okResult(userLoginVo);
    }
}
