package com.ht.common.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-04-29 15:24
 **/
public class IndexCache {

    private static final Cache<String, Object> CACHE = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    private IndexCache() {
    }


    /**
     * 删除数据
     *
     * @param key
     */
    public static void remove(String key) {
        CACHE.invalidate(key);


    }

    /**
     * 获取数据 如果缓存没有key 返回null
     *
     * @param key
     * @return Object
     */
    public static Object get(String key) {
        return CACHE.getIfPresent(key);
    }

    /**
     * 存入缓存
     *
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        CACHE.put(key, value);
    }
}
