package com.nene.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName BlogUserLoginVo
 * @Description 博客登录用户数据封装类
 * @Author Protip
 * @Date 2023/1/9 14:40
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserLoginVo {

    private String token;
    private UserInfo userInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {

        /**
         * 主键
         */
        private Long id;

        /**
         * 昵称
         */
        private String nickName;

        /**
         * 邮箱
         */
        private String email;

        /**
         * 性别（0男 1女 2双性）
         */
        private String sex;

        /**
         * 头像
         */
        private String avatar;
    }
}
