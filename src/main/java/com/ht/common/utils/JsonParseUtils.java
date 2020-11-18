package com.ht.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @description: 读取json文件
 * @author: yaKun.shi
 * @create: 2019-12-30 16:49
 **/
public class JsonParseUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonParseUtils.class);

    public static JSONObject getJsonObject(String path) {
        String path1 = Thread.currentThread().getContextClassLoader().getResource("2c7ee15885e14ff786b5ac066c659cd7.json").getPath();
        File file = new File(path1);
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        JSONObject menuObject = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int a;
            while ((a = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, a);
            }
            String stringMenu = new String(buffer);
            menuObject = JSONObject.parseObject(stringMenu);
        } catch (Exception e) {
            logger.error("服务左侧菜单json解析出现错误:", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return menuObject;
    }
}
