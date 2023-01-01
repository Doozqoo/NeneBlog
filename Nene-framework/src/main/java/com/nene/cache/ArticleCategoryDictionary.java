package com.nene.cache;

import com.nene.constants.SystemConstants;
import com.nene.domain.entity.ArticleCategory;
import com.nene.service.ArticleCategoryService;
import com.nene.utils.SpringContextUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleCategoryDictionary
 * @Description 文章分类字典
 * @Author Protip
 * @Date 2022/12/31 18:49
 * @Version 1.0
 */
public class ArticleCategoryDictionary {

    private static final ArticleCategoryService articleCategoryService = SpringContextUtil.getBean(ArticleCategoryService.class);

    public static final Map<Long, String> dictionaryMap = new HashMap<>();

    public static void put(Long key, String value){
        dictionaryMap.put(key, value);
    }

    public static void put(ArticleCategory articleCategory){
       put(articleCategory.getId(), articleCategory.getName());
    }

    public static void put(List<ArticleCategory> list) {
        list.forEach(ArticleCategoryDictionary::put);
    }

    public static boolean containsKey(Long key){
        return dictionaryMap.containsKey(key);
    }

    public static String translate(Long key){
        if(!containsKey(key)){
            update();
        }
        return dictionaryMap.get(key);
    }

    public static void deleteAll(){
        dictionaryMap.clear();
    }

    public static void update(){
        deleteAll();
        List<ArticleCategory> articleCategories = articleCategoryService.lambdaQuery()
                .select(ArticleCategory::getId,
                        ArticleCategory::getName)
                .eq(ArticleCategory::getStatus, SystemConstants.ARTICLE_CATEGORY_STATUS_NORMAL)
                .list();
        put(articleCategories);
    }
}
