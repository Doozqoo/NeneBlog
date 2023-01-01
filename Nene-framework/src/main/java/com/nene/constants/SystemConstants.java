package com.nene.constants;

/**
 * @author Ayachi Nene
 */
public class SystemConstants {

    /**
     * 文章没有置顶
     */
    public static final Character ARTICLE_TOP_OFF = '0';

    /**
     * 文章置顶
     */
    public static final Character ARTICLE_TOP_ON = '1';

    /**
     * 文章属于草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     * 正常已发布的文章
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 文章分类禁用
     */
    public static final int ARTICLE_CATEGORY_STATUS_DISABLE = 1;

    /**
     * 文章分类正常
     */
    public static final int ARTICLE_CATEGORY_STATUS_NORMAL = 0;

    /**
     * 友链未审核状态
     */
    public static final int LINK_STATUS_UNAPPROVED = 0;

    /**
     * 友链审核已通过状态
     */
    public static final int LINK_STATUS_APPROVED = 1;

    /**
     * 友链审核未通过状态
     */
    public static final int LINK_STATUS_FAILED = 2;

}
