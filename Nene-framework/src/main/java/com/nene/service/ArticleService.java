package com.nene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nene.domain.ResponseResult;
import com.nene.domain.entity.Article;

/**
 * @author Ayachi Nene
 * @description 针对表【nene_article】的数据库操作Service
 * @createDate 2022-12-24 18:20:31
 */
public interface ArticleService extends IService<Article> {

    /**
     * 获取热门文章
     *
     * @return 返回热门文章对象集合
     */
    ResponseResult getHotArticleList();

    /**
     * 获取文章列表
     *
     * @param pageNum    页码
     * @param pageSize   每页展示数量
     * @param categoryId 分类id
     * @return 返回文章对象集合
     */
    ResponseResult getArticleSnapshotList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 获取置顶文章
     *
     * @param categoryId 分类id
     * @return 返回置顶文章对象集合
     */
    ResponseResult getTopArticleSnapshotList(Long categoryId);

    /**
     * 获取文章详情数据
     *
     * @param articleId 文章id
     * @return 文章详情数据
     */
    ResponseResult getArticleDetail(Long articleId);
}
