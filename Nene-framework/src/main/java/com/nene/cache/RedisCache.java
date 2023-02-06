package com.nene.cache;

import com.nene.utils.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisCache
 * @Description redis缓存服务工具
 * @Author Protip
 * @Date 2023/1/9 14:28
 * @Version 1.0
 */
@Component
public class RedisCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setValue(String k, Object o) {
        redisTemplate.opsForValue().set(k, o);
    }

    public void setHashMap(String k, Map<String, ?> m) {
        redisTemplate.opsForHash().putAll(k, m);
    }

    public void setHashMap(String k, String hk, Object v) {
        redisTemplate.opsForHash().put(k, hk, v);
    }

    public <T> T getValue(String k, Class<T> clazz) {
        return clazz.cast(getValue(k));
    }

    public Object getValue(String k) {
        return redisTemplate.opsForValue().get(k);
    }

    public Map getHashMap(String k) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(k);
        return entries;
    }

    public <T> T getValue(String k, String hk, Class<T> clazz) {
        String jsonStr = String.valueOf(redisTemplate.opsForHash().get(k, hk));
        if (!StringUtils.hasText(jsonStr) || "null".equals(jsonStr)) {
            return null;
        }
        return JacksonUtil.readValue(jsonStr, clazz);
    }

    public void increment(String k, String hk, long v) {
        redisTemplate.boundHashOps(k).increment(hk, v);
    }

    public void expire(String k, long l, TimeUnit timeUnit) {
        redisTemplate.expire(k, l, timeUnit);
    }

    public void delValue(String k) {
        redisTemplate.delete(k);
    }

    public void delValue(String k, String hk) {
        redisTemplate.opsForHash().delete(k, hk);
    }
}
