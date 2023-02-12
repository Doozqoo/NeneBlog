package com.nene.constants;

/**
 * @ClassName RedisKey
 * @Description redis钥匙管理类
 * @Author Protip
 * @Date 2023/2/1 18:48
 * @Version 1.0
 */
public class RedisConstants {
    // ==================================== time ====================================

    public static final long TIMEOUT_REMEMBER = 24 * 7L;

    public static final long TIMEOUT_DEFAULT = 6L;

    // ==================================== key ====================================

    public static final String ARTICLE_VIEW_COUNT = "Article_ViewCount";

    public static final String BLOG_LOGIN = "BlogLogin_";

    public static final String BLOG_AUTHOR_LOGIN = "AuthorLogin_";
}
