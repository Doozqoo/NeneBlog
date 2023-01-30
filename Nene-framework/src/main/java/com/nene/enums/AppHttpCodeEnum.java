package com.nene.enums;

/**
 * @author Ayachi Nene
 */
public enum AppHttpCodeEnum {

    SUCCESS(200, "操作成功"),
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "没有权限操作"),
    SYSTEM_ERROR(500, "系统异常"),
    USERNAME_EXIST(601, "用户名已存在"),
    PHONE_NUMBER_EXIST(602, "手机号已存在"),
    EMAIL_EXIST(603, "邮箱已存在"),
    REQUIRE_USERNAME(604, "必须填写用户名"),
    LOGIN_ERROR(605, "账户或密码错误"),
    USER_NOT_EXIST(606, "用户不存在"),
    USER_STATUS_CLOSED(606, "用户封禁中");

    int code;
    String msg;

    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
