package com.nene.init;

import com.nene.cache.RedisCache;
import com.nene.constants.RedisConstants;
import com.nene.domain.entity.Article;
import com.nene.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ArticleViewCountInit
 * @Description 文章浏览量数据初始化
 * @Author Protip
 * @Date 2023/2/1 18:37
 * @Version 1.0
 */
@Component
@RequiredArgsConstructor
public class ArticleViewCountInit implements CommandLineRunner {

    private final ArticleService articleService;
    private final RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Article> articles = articleService.lambdaQuery()
                .select(Article::getId,
                        Article::getViewCount)
                .list();

        Map<String, Long> map = articles.stream()
                .collect(Collectors.toMap(article -> String.valueOf(article.getId()), Article::getViewCount));

        redisCache.setHashMap(RedisConstants.ARTICLE_VIEW_COUNT, map);
    }
}
