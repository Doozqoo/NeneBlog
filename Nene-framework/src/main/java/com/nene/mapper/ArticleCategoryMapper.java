package com.nene.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nene.domain.entity.ArticleCategory;
import org.springframework.stereotype.Repository;

/**
 * @author Ayachi Nene
 * @description 针对表【nene_article_category(文章分类设置表)】的数据库操作Mapper
 * @createDate 2022-12-29 15:35:13
 * @Entity com.nene..domain.entity.Category
 */
@Repository
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

}




