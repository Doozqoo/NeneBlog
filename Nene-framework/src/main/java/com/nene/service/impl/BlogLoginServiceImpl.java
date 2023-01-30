package com.nene.service.impl;

import com.nene.cache.RedisCache;
import com.nene.domain.ResponseResult;
import com.nene.domain.dto.BlogUserLoginDto;
import com.nene.domain.entity.User;
import com.nene.domain.vo.BlogUserLoginVo;
import com.nene.service.BlogLoginService;
import com.nene.service.UserService;
import com.nene.utils.BeanCopyUtil;
import com.nene.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @ClassName BlogLoginServiceImpl
 * @Description 博客登录接口实现类
 * @Author Protip
 * @Date 2023/1/7 18:15
 * @Version 1.0
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(BlogUserLoginDto blogUserLoginDto) {

        // 登录授权
        Authentication authentication = new UsernamePasswordAuthenticationToken(blogUserLoginDto.getAccount(), blogUserLoginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate == null) {
            throw new RuntimeException("密码错误！");
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
                .eq(User::getEmail, blogUserLoginDto.getAccount())
                .or()
                .eq(User::getPhoneNumber, blogUserLoginDto.getAccount())
                .or()
                .eq(User::getUserName, blogUserLoginDto.getAccount())
                .one();

        // 在redis中缓存用户数据
        long expiration = blogUserLoginDto.isRemember() ? 24 * 7L : 6L;
        redisCache.setValue("BlogLogin_" + user.getId(), user, expiration);

        // 生成token返回
        String token = JwtUtil.getToken(user.getId());
        BlogUserLoginVo.UserInfo userInfo = BeanCopyUtil.beanCopy(user, BlogUserLoginVo.UserInfo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(token, userInfo);

        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout(User user) {
        String key = "BlogLogin_" + user.getId();
        redisCache.delValue(key);
        return ResponseResult.okResult("已成功注销！");
    }
}
