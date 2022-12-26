package com.nene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.domain.entity.Article;
import com.nene.mapper.ArticleMapper;
import com.nene.service.ArticleService;
import org.springframework.stereotype.Service;

/**
* @author Ayachi Nene
* @description 针对表【nene_article】的数据库操作Service实现
* @createDate 2022-12-24 18:20:31
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {

}




