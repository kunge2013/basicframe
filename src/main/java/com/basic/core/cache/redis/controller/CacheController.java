package com.basic.core.cache.redis.controller;

import com.basic.core.service.ICache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/4/17
 * Time：22:56
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
public class CacheController {

    @Autowired
    private ICache cache;

    @RequestMapping(value = "/pop")
    public Object pop(String key)
    {
        return cache.getData(key);
    }
    @RequestMapping(value = "/push")
    public Object pop(String key,String value)
    {
        return cache.putData(key, value);
    }

}
