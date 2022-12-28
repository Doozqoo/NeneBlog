package com.nene.controller;

import com.nene.domain.ResponseResult;
import com.nene.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ayachi Nene
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hot")
    public ResponseResult hotArticleList() {
        return articleService.getHotArticleList();
    }
}
