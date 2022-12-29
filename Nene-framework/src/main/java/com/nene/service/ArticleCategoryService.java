package com.nene.service;

import com.nene.domain.ResponseResult;
import com.nene.domain.entity.ArticleCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Ayachi Nene
 * @description 针对表【nene_article_category(文章分类设置表)】的数据库操作Service
 * @createDate 2022-12-29 15:35:13
 */
public interface ArticleCategoryService extends IService<ArticleCategory> {

    /**
     * 获取文章分类列表
     * @return 分类的id、name对象集合
     */
    ResponseResult getArticleCategoryList();
}
