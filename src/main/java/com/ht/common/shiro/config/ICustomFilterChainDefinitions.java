package com.ht.common.shiro.config;

import java.util.Map;

/**
 * @date 2020/3/5 11:28
 * @desc
 * @author yakun.shi
 */
public interface ICustomFilterChainDefinitions {
    /**
     * shiro过虑链
     * @return
     */
    Map<String, String> loadFilterChainDefinitions();

}
