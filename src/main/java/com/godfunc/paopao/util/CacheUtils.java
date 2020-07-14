package com.godfunc.paopao.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author Godfunc
 * @date 2020/2/7 19:52
 */
public class CacheUtils {

    private volatile Cache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(5000)
            .build();

    private static final CacheUtils instance = new CacheUtils();

    private CacheUtils() {
    }

    public static CacheUtils getInstance() {
        return instance;
    }

    public void putCache(String key, String value) {
        cache.put(key, value);
    }

    public String getCache(String key) {
        return cache.getIfPresent(key);
    }

    public void removeCache(String key) {
        cache.invalidate(key);
    }
}
