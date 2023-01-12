package com.nene.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @ClassName CommentVo
 * @Description 评论数据封装类
 * @Author Protip
 * @Date 2023/1/11 15:02
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentVo {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 根评论id
     */
    private Long rootId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论回复指向的对象id
     */
    private Long toCommentUserId;

    /**
     * 论回复指向的对像名称
     */
    private String toCommentUsername;

    /**
     * 评论回复所指向的评论id
     */
    private Long toCommentId;

    /**
     * 发表人id
     */
    private Long createBy;

    /**
     * 发表人
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 发表时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 子评论
     */
    private PageVo subordinateComments;
}
