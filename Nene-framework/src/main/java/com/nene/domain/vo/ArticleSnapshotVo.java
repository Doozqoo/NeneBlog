package com.nene.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName ListArticleVo
 * @Description 文章列表数据封装类
 * @Author Protip
 * @Date 2022/12/31 17:56
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSnapshotVo {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 所属分类id
     */
    private Long categoryId;

    /**
     * 所属分类名称
     */
    private String categoryName;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 访问量
     */
    private Long viewCount;

    /**
     * 是否置顶（0否 1是）
     */
    private String isTop;

    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
