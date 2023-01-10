package com.nene.utils;

import com.nene.domain.entity.User;

/**
 * @ClassName ThreadLocalUtil
 * @Description 线程工具类
 * @Author Protip
 * @Date 2023/1/10 17:06
 * @Version 1.0
 */
public class ThreadLocalUtil {

    private final static ThreadLocal<User> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(User u) {
        THREAD_LOCAL.set(u);
    }

    public static User getUser() {
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
