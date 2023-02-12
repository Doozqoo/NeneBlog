package com.nene.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.cache.ArticleCategoryDictionary;
import com.nene.cache.RedisCache;
import com.nene.constants.RedisConstants;
import com.nene.constants.SystemConstants;
import com.nene.domain.ResponseResult;
import com.nene.domain.entity.Article;
import com.nene.domain.vo.ArticleDetailVo;
import com.nene.domain.vo.ArticleSnapshotVo;
import com.nene.domain.vo.HotArticleVo;
import com.nene.domain.vo.PageVo;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.mapper.ArticleMapper;
import com.nene.service.ArticleService;
import com.nene.utils.BeanCopyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Ayachi Nene
 * @description 针对表【nene_article】的数据库操作Service实现
 * @createDate 2022-12-24 18:20:31
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    private final RedisCache redisCache;

    @Override
    public ResponseResult getHotArticleList() {
        /**
         * 查询条件：
         *  1、必须是正式文章
         *  2、按浏览量排序
         *  3、最大显示10条
         */
        List<Article> articles = this.lambdaQuery()
                .select(Article::getId,
                        Article::getTitle,
                        Article::getViewCount)
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(Article::getViewCount)
                .last("limit 0, 10")
                .list();

        if (articles == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        // 将实体对象拷贝到vo对象
        List<HotArticleVo> hotArticleVos = BeanCopyUtil.beanListCopy(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult getTopArticleSnapshotList(Long categoryId) {

        List<Article> articles = this.lambdaQuery()
                .select(Article::getId,
                        Article::getTitle,
                        Article::getSummary,
                        Article::getCategoryId,
                        Article::getThumbnail,
                        Article::getCommentCount,
                        Article::getViewCount,
                        Article::getIsTop,
                        Article::getCreateBy,
                        Article::getCreateTime)
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .eq(Article::getIsTop, SystemConstants.ARTICLE_TOP_ON)
                .eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId)
                .orderByDesc(Article::getCreateTime)
                .list();

        List<ArticleSnapshotVo> articleSnapshotVos = BeanCopyUtil.beanListCopy(articles, ArticleSnapshotVo.class);

        for (ArticleSnapshotVo vo : articleSnapshotVos) {
            vo.setCategoryName(ArticleCategoryDictionary.translate(vo.getCategoryId()));
        }

        return ResponseResult.okResult(articleSnapshotVos);
    }

    @Override
    public ResponseResult getArticleSnapshotList(Integer pageNum, Integer pageSize, Long categoryId) {

        Page<Article> page = new Page<>(pageNum, pageSize);
        this.lambdaQuery()
                .select(Article::getId,
                        Article::getTitle,
                        Article::getSummary,
                        Article::getCategoryId,
                        Article::getThumbnail,
                        Article::getCommentCount,
                        Article::getViewCount,
                        Article::getIsTop,
                        Article::getCreateBy,
                        Article::getCreateTime)
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .eq(Article::getIsTop, SystemConstants.ARTICLE_TOP_OFF)
                .eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId)
                .orderByDesc(Article::getCreateTime)
                .page(page);

        List<ArticleSnapshotVo> articleSnapshotVos = BeanCopyUtil.beanListCopy(page.getRecords(), ArticleSnapshotVo.class);

        for (ArticleSnapshotVo vo : articleSnapshotVos) {
            vo.setCategoryName(ArticleCategoryDictionary.translate(vo.getCategoryId()));
        }

        return ResponseResult.okResult(new PageVo(articleSnapshotVos, page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetail(Long articleId) {

        Article article = this.getById(articleId);
        if (Objects.isNull(article)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "文章已被移除");
        }
        ArticleDetailVo articleDetailVo = BeanCopyUtil.beanCopy(article, ArticleDetailVo.class);
        articleDetailVo.setCategoryName(ArticleCategoryDictionary.translate(articleDetailVo.getCategoryId()));
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long articleId) {

        redisCache.increment(RedisConstants.ARTICLE_VIEW_COUNT, String.valueOf(articleId), 1);
        return ResponseResult.okResult();
    }

}




