package com.nene.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName CommentDto
 * @Description 新评论数据传输对象
 * @Author Protip
 * @Date 2023/1/13 11:32
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    /**
     * 评论类型
     */
    private String type;

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
     * 评论回复所指向的评论id
     */
    private Long toCommentId;
}
