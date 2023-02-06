package com.nene.controller;

import com.nene.annotation.ApiLog;
import com.nene.domain.ResponseResult;
import com.nene.domain.dto.CommentDto;
import com.nene.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CommentController
 * @Description 评论请求控制接口
 * @Author Protip
 * @Date 2023/1/11 12:07
 * @Version 1.0
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiLog(name = "评论列表")
    @GetMapping("/list")
    public ResponseResult commentPage(@RequestParam("id") Long articleId, @RequestParam("p") Integer pageNum, @RequestParam("n") Integer pageSize) {
        return commentService.getCommentPage(articleId, pageNum, pageSize);
    }

    @ApiLog(name = "子评论列表")
    @GetMapping("/subordinate/list")
    public ResponseResult subordinateCommentPage(@RequestParam("id") Long rootId, @RequestParam("p") Integer pageNum, @RequestParam("n") Integer pageSize) {
        return commentService.getSubordinateCommentPage(rootId, pageNum, pageSize);
    }

    @ApiLog(name = "发送评论")
    @PostMapping("/send")
    public ResponseResult publishComment(@RequestBody CommentDto commentDto) {
        return commentService.publishComment(commentDto);
    }
}
