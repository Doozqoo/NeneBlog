package com.nene.controller;

import com.nene.annotation.ApiLog;
import com.nene.domain.ResponseResult;
import com.nene.service.ArticleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ayachi Nene
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/article/category")
public class ArticleCategoryController {

    private final ArticleCategoryService articleCategoryService;

    @ApiLog(name = "文章分类列表")
    @GetMapping("/list")
    public ResponseResult getArticleCategoryList() {
        return articleCategoryService.getArticleCategoryList();
    }
}
