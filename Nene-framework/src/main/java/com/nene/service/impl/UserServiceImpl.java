package com.nene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.domain.ResponseResult;
import com.nene.domain.entity.User;
import com.nene.domain.vo.UserInfoVo;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.exception.CustomServiceException;
import com.nene.mapper.UserMapper;
import com.nene.service.UserService;
import com.nene.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

/**
 * @author zhish
 * @description 针对表【sys_user】的数据库操作Service实现
 * @createDate 2023-01-02 13:45:40
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public ResponseResult getUserInfo(Long userId) {
        if(userId == null){
            throw new CustomServiceException(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        User user = this.lambdaQuery()
                .select(User::getId,
                        User::getUserName,
                        User::getNickName,
                        User::getEmail,
                        User::getSex,
                        User::getAvatar,
                        User::getCreateTime)
                .eq(User::getId, userId)
                .one();

        if(user == null){
            return ResponseResult.okResult("没有查询到用户数据！");
        }

        UserInfoVo userInfoVo = BeanCopyUtil.beanCopy(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }
}




