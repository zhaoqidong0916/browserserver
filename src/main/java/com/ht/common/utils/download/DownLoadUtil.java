package com.ht.common.utils.download;

import com.ht.common.utils.upload.UploadProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * @ClassName DownLoadUtil
 * @Author yakun.shi
 * @Date 2019/7/2 10:16
 * @Version 1.0
 **/
public class DownLoadUtil {

    private static Logger logger = LoggerFactory.getLogger(DownLoadUtil.class);


    private static HashMap<String, String> photos = new HashMap<>();
    private static HashMap<String, String> videos = new HashMap<>();

    static {
        photos.put("jpg", "image/jpeg");
        photos.put("JPG", "image/jpeg");
        photos.put("jpeg", "image/jpeg");
        photos.put("JPEG", "image/jpeg");
        photos.put("png", "image/png");
        photos.put("PNG", "image/png");
    }

    static {
        videos.put("avi", "video/avi");
        videos.put("mp4", "video/mpeg4");
        videos.put("MP4", "video/mpeg4");
        videos.put("wmv", "video/x-ms-wmv");
        videos.put("WMV", "video/x-ms-wmv");
        photos.put("mpeg", "video/mpg");
        photos.put("MPEG", "video/mpg");
        photos.put("mpg", "video/mpg");
        photos.put("MPG", "video/mpg");
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description 下载文件
     * @Return
     * @Date 2019/12/10 17:00
     */
    public static void downLoad(String fileName, HttpServletResponse response, UploadProperties uploadProperties) {
        String name = uploadProperties.getPath() + fileName;
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        boolean flag = true;
        String contentType = "application/octet-stream;charset=UTF-8";
        String[] split = StringUtils.split(fileName, ".");
        String suffix = split[split.length - 1];
        if (photos.get(suffix) != null) {
            contentType = photos.get(suffix);
            flag = false;
        }
        if (videos.get(suffix) != null) {
            contentType = videos.get(suffix);
            flag = false;
        }
        try {
            File file = new File(name);
            fileInputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            if (flag) {
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            }
            int length = (int) file.length();
            response.setContentLength(length);
            response.setContentType(contentType);
            byte[] buffer = new byte[1024];
            int a;
            while ((a = fileInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, a);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
