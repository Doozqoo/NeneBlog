package com.nene.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserInfoUpdateDto
 * @Description 用户数据编辑数据传输类
 * @Author Protip
 * @Date 2023/1/31 15:25
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别（0男 1女 2双性）
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

}
