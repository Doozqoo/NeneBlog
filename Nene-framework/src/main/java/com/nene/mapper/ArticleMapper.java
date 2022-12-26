package com.nene.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nene.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Ayachi Nene
* @description 针对表【nene_article】的数据库操作Mapper
* @createDate 2022-12-24 18:20:31
* @Entity domain.entity.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}




