package com.nene.controller;

import com.nene.domain.entity.Article;
import com.nene.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ayachi Nene
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /* @GetMapping("/test")
    public List<Article> test(){
        List<Article> list = articleService.list();
        return list;
    } */
}
