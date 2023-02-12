package com.nene.controller;

import com.nene.annotation.ApiLog;
import com.nene.domain.ResponseResult;
import com.nene.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ayachi Nene
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @ApiLog(name = "热门文章列表")
    @GetMapping("/list/hot")
    public ResponseResult hotArticleList() {
        return articleService.getHotArticleList();
    }

    @ApiLog(name = "置顶文章列表")
    @GetMapping("/list/top")
    public ResponseResult getTopArticleList(Long categoryId) {
        return articleService.getTopArticleSnapshotList(categoryId);
    }

    @ApiLog(name = "文章列表")
    @GetMapping("/list")
    public ResponseResult getArticleList(@RequestParam("p") Integer pageNum, @RequestParam("n") Integer pageSize, @RequestParam(name = "c", required = false) Long categoryId) {
        return articleService.getArticleSnapshotList(pageNum, pageSize, categoryId);
    }

    @ApiLog(name = "文章详情页")
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long articleId) {
        return articleService.getArticleDetail(articleId);
    }

    @ApiLog(name = "更新文章浏览量")
    @PutMapping("/view/add/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long articleId) {
        return articleService.updateViewCount(articleId);
    }
}
