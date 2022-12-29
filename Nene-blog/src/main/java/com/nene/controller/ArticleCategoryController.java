package com.nene.controller;

import com.nene.domain.ResponseResult;
import com.nene.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ayachi Nene
 */
@RestController
@RequestMapping("/article/category")
public class ArticleCategoryController {

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @GetMapping("/list")
    public ResponseResult getArticleCategoryList(){
        return articleCategoryService.getArticleCategoryList();
    }
}
