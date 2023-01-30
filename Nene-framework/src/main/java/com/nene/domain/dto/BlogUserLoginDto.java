package com.nene.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserDto
 * @Description 用户数据传输类
 * @Author Protip
 * @Date 2023/1/7 18:09
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserLoginDto {

    /**
     * 用户名
     */
    private String account;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 是否持久保存登录
     */
    private boolean remember = false;

}
