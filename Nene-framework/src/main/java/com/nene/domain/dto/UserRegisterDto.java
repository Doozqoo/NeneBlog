package com.nene.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserRegisterDto
 * @Description 用户注册相关数据传输类
 * @Author Protip
 * @Date 2023/1/31 15:53
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

}
