package com.nene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.constants.SystemConstants;
import com.nene.domain.ResponseResult;
import com.nene.domain.entity.Article;
import com.nene.domain.vo.HotArticleVo;
import com.nene.enums.AppHttpCodeEnum;
import com.nene.mapper.ArticleMapper;
import com.nene.service.ArticleService;
import com.nene.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ayachi Nene
 * @description 针对表【nene_article】的数据库操作Service实现
 * @createDate 2022-12-24 18:20:31
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

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

        if(articles == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        // 将实体对象拷贝到vo对象
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.beanListCopy(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }
}




