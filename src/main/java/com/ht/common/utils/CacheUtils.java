package com.ht.common.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * @description: 缓存工具
 * @author: yaKun.shi
 * @create: 2020-03-31 10:03
 **/
public class CacheUtils {


    private static long duration = 60;

    private static TimeUnit timeUnit = TimeUnit.MINUTES;

    /**
     * 获取cache对象
     */
    private static final Cache<String, Object> CACHE = Caffeine.newBuilder()
            .expireAfterWrite(duration, timeUnit)
            .expireAfterAccess(duration, timeUnit)
            .maximumSize(100)
            .build();

    private CacheUtils() {
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
