package com.basic.core.service.impl;

import com.basic.core.service.ICache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CacheServce implements ICache {
    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;
    @Override
    public Object putData(Object key, Object value) {
        return redisTemplate.opsForList().leftPush(key,value);
    }
    @Override
    public Object getData(Object key) {
        return redisTemplate.opsForList().leftPop(key);
    }
}
