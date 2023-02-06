package com.nene.task;

import com.nene.cache.RedisCache;
import com.nene.constants.RedisConstants;
import com.nene.domain.entity.Article;
import com.nene.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ArticleTask
 * @Description 文章相关定时任务
 * @Author Protip
 * @Date 2023/2/2 10:52
 * @Version 1.0
 */
@Component
public class ArticleTask {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateViewCount(){
        Map<String, Integer> map = redisCache.getHashMap(RedisConstants.ARTICLE_VIEW_COUNT);
        List<Article> articles = map.entrySet().stream()
                .map(entry -> {
                    Article article = new Article();
                    article.setId(Long.valueOf(entry.getKey()));
                    article.setViewCount(Long.valueOf(entry.getValue()));
                    return article;
                })
                .collect(Collectors.toList());
        articleService.updateBatchById(articles, 200);
    }
}
