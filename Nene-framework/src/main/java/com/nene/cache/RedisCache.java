package com.nene.cache;

import com.nene.utils.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    private StringRedisTemplate stringRedisTemplate;

    public void setValue(String k, String v) {
        stringRedisTemplate.opsForValue().set(k, v);
    }

    public void setValue(String k, Object o) {
        String v = JacksonUtil.writeValueAsString(o);
        stringRedisTemplate.opsForValue().set(k, v, 2, TimeUnit.HOURS);
    }

    public <T> T getValue(Object k, Class<T> clazz) {
        String jsonStr = stringRedisTemplate.opsForValue().get(k);
        if (!StringUtils.hasText(jsonStr)) {
            return null;
        }
        return JacksonUtil.readValue(jsonStr, clazz);
    }

    public boolean delValue(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
    }
}
