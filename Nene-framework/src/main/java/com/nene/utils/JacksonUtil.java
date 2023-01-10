package com.nene.utils;

import com.alibaba.excel.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JacksonUtil
 * @Description Jackson工具类
 * @Author Protip
 * @Date 2023/1/9 13:56
 * @Version 1.0
 */
public class JacksonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 所有的日期都统一用yyyy-MM-dd HH:mm:ss格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 忽略字符串存在，对象不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 属性为NULL不被序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 对象转json字符串
     *
     * @param value 需要转换的对象
     * @return json格式字符串
     */
    public static String writeValueAsString(Object value) {
        String result = StringUtils.EMPTY;
        try {
            result = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转自定义对象
     *
     * @param content   需要转换的字符串
     * @param valueType 需要转换后的类型
     * @param <T>       泛型
     * @return 转换后对象
     */
    public static <T> T readValue(String content, Class<T> valueType) {
        T result = null;
        try {
            result = objectMapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * json字符串转换为List
     *
     * @param content   需要转换的字符串
     * @param valueType 需要转换后的类型
     * @param <T>       泛型
     * @return 转换后的集合
     */
    public static <T> List<T> readValueAsList(String content, Class<T> valueType) {
        List<T> list = new ArrayList<>();
        try {
            list = objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return list;
    }
}
