package com.nene.controller;

import com.nene.domain.ResponseResult;
import com.nene.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ayachi Nene
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list/hot")
    public ResponseResult hotArticleList() {
        return articleService.getHotArticleList();
    }

    @GetMapping("/list/top")
    public ResponseResult getTopArticleList(Long categoryId) {
        return articleService.getTopArticleSnapshotList(categoryId);
    }

    @GetMapping("/list")
    public ResponseResult getArticleList(@RequestParam("p") Integer pageNum, @RequestParam("n") Integer pageSize, @RequestParam(name = "c", required = false) Long categoryId) {
        return articleService.getArticleSnapshotList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long articleId){
        return articleService.getArticleDetail(articleId);
    }
}
