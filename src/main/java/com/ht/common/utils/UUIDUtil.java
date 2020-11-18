package com.ht.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @ClassName UUIDUtil
 * @Author yakun.shi
 * @Date 2019/6/14 15:52
 * @Version 1.0
 **/
public class UUIDUtil {

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        String replace = StringUtils.replace(s, "-", "");
        return replace;
    }
}
