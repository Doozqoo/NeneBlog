package com.nene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.domain.ResponseResult;
import com.nene.domain.dto.UserRegisterDto;
import com.nene.domain.dto.UserUpdateDto;
import com.nene.domain.entity.User;
import com.nene.domain.vo.UserInfoVo;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.exception.CustomServiceException;
import com.nene.mapper.UserMapper;
import com.nene.service.UserService;
import com.nene.utils.BeanCopyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author zhish
 * @description 针对表【sys_user】的数据库操作Service实现
 * @createDate 2023-01-02 13:45:40
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult getUserInfo(Long userId) {
        if (userId == null) {
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

        if (user == null) {
            return ResponseResult.okResult("没有查询到用户数据！");
        }

        UserInfoVo userInfoVo = BeanCopyUtil.beanCopy(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(UserUpdateDto dto) {
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.EMPTY_DATA);
        }
        User user = BeanCopyUtil.beanCopy(dto, User.class);
        this.updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(UserRegisterDto dto) {
        // 参数非空校验
        boolean verification = StringUtils.hasText(dto.getUserName())
                && StringUtils.hasText(dto.getNickName())
                && StringUtils.hasText(dto.getEmail())
                && StringUtils.hasText(dto.getPassword());
        if (!verification) {
            throw new CustomServiceException(AppHttpCodeEnum.EMPTY_DATA);
        }

        User user = BeanCopyUtil.beanCopy(dto, User.class);
        // 用户数据重复校验
        verification = this.checkUserName(user.getUserName());
        if (verification) {
            throw new CustomServiceException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        verification = this.checkEmail(user.getEmail());
        if (verification) {
            throw new CustomServiceException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 用户保存
        this.save(user);

        return ResponseResult.okResult();
    }

    private boolean checkUserName(String username) {
        Long count = this.lambdaQuery()
                .eq(User::getUserName, username)
                .count();
        return count > 0;
    }

    private boolean checkEmail(String email) {
        Long count = this.lambdaQuery()
                .eq(User::getEmail, email)
                .count();
        return count > 0;
    }
}




