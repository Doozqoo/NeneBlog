package com.nene.service;

import com.nene.domain.ResponseResult;
import com.nene.domain.dto.CommentDto;
import com.nene.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhish
* @description 针对表【nene_comment(评论数据表)】的数据库操作Service
* @createDate 2023-01-10 18:43:10
*/
public interface CommentService extends IService<Comment> {

    /**
     * 获取文章评论列表
     * @param articleId 文章id
     * @param pageNum 当前页码
     * @param pageSize 每页携带的评论数量
     * @return 评论列表数据
     */
    ResponseResult getCommentPage(Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 查询根评论的子评论
     * @param rootId 根评论id
     * @param pageNum 当前页码
     * @param pageSize 每页携带的评论数量
     * @return 子评论列表数据
     */
    ResponseResult getSubordinateCommentPage(Long rootId, Integer pageNum, Integer pageSize);

    /**
     * 发表评论
     * @param commentDto 评论数据
     * @return 接口执行结果
     */
    ResponseResult publishComment(CommentDto commentDto);
}
