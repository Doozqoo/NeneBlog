package com.nene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.constants.SystemConstants;
import com.nene.domain.ResponseResult;
import com.nene.domain.entity.Article;
import com.nene.domain.entity.ArticleCategory;
import com.nene.domain.vo.ArticleCategoryVo;
import com.nene.mapper.ArticleCategoryMapper;
import com.nene.service.ArticleCategoryService;
import com.nene.service.ArticleService;
import com.nene.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ayachi Nene
 * @description 针对表【nene_article_category(文章分类设置表)】的数据库操作Service实现
 * @createDate 2022-12-29 15:35:13
 */
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory>
        implements ArticleCategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getArticleCategoryList() {

        // 查询已发布的文章
        List<Article> articles = articleService.lambdaQuery()
                .select(Article::getCategoryId)
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .list();

        // 对文章的分类id提取和去重
        List<Long> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .distinct()
                .collect(Collectors.toList());

        // 查询文章分类的数据
        List<ArticleCategory> categories = this.lambdaQuery()
                .select(ArticleCategory::getId,
                        ArticleCategory::getName)
                .in(ArticleCategory::getId, categoryIds)
                .eq(ArticleCategory::getStatus, SystemConstants.ARTICLE_CATEGORY_STATUS_NORMAL)
                .list();

        List<ArticleCategoryVo> categoryVos = BeanCopyUtil.beanListCopy(categories, ArticleCategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}




