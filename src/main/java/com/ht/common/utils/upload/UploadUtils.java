package com.ht.common.utils.upload;

import com.ht.common.exceptions.exception.UploadException;
import com.ht.common.utils.HandleUtils;
import com.ht.common.utils.HttpUtil;
import com.ht.common.utils.ToolMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: browser_template
 * @description: 上传工具
 * @author: yaKun.shi
 * @create: 2019-09-20 10:33
 **/
@Component
public class UploadUtils {

    @Autowired
    private UploadProperties uploadProperties;

    private static final String DOWN = "download/";


    public String upload(MultipartFile file) {
        String downLoadPath = null;
        try {
            String randomName = HandleUtils.getRandomName(file.getOriginalFilename());
            httpUpload(file, randomName);
            String url = "http://" + uploadProperties.getIp() + ":" + uploadProperties.getPort() + uploadProperties.getSuffixName() + DOWN;
            downLoadPath = url + randomName;

        } catch (Exception e) {
            throw new UploadException(e);
        }
        return downLoadPath;
    }


    public String upload(MultipartFile file, String fileName) {
        String downLoadPath = null;
        try {
            String randomName = HandleUtils.getRandomName(fileName);
            httpUpload(file, randomName);
            String url = "http://" + uploadProperties.getIp() + ":" + uploadProperties.getPort() + uploadProperties.getSuffixName() + DOWN;
            downLoadPath = url + randomName;
        } catch (Exception e) {
            throw new UploadException(e);
        }
        return downLoadPath;
    }

    /**
     * @param file
     * @param fileName
     * @param flag
     * @return
     */
    public Map<String, String> upload(MultipartFile file, String fileName, boolean flag) {
        String downLoadPath = null;
        Map<String, String> map = null;
        InputStream inputStream = null;
        try {
            String randomName = HandleUtils.getRandomName(fileName);
            httpUpload(file, randomName);
            String url = "http://" + uploadProperties.getIp() + ":" + uploadProperties.getPort() + uploadProperties.getSuffixName() + DOWN;
            downLoadPath = url + randomName;
            map = new HashMap<>(4);
            map.put("downloadUrl", downLoadPath);
            if (flag) {
                inputStream = file.getInputStream();
                //计算文件md5
                String s = ToolMD5.encodeSha256HexByte(file.getBytes());
                map.put("md5", s);
            }
        } catch (Exception e) {
            throw new UploadException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return map;
    }

    private void httpUpload(MultipartFile file, String fileName) {
        HttpUtil.upload(file, fileName, uploadProperties.getPath());
    }

    private void httpUpload(MultipartFile file) {
        HttpUtil.upload(file, uploadProperties.getPath());
    }


}
