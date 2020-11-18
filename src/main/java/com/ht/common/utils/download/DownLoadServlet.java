package com.ht.common.utils.download;

import com.ht.common.config.countratelimiteraop.CountRateLimiterAdd;
import com.ht.common.utils.upload.UploadProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: ht_back
 * @description: 下载文件servlet
 * @author: yaKun.shi
 * @create: 2019-12-11 11:40
 **/
@RestController
public class DownLoadServlet {

    @RequestMapping("/download/*")
    @CountRateLimiterAdd
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
        UploadProperties uploadProperties = webApplicationContext.getBean(UploadProperties.class);
        String s = req.getRequestURL().toString();
        String[] split = s.split("/download/");
        String fileName = split[1];
        DownLoadUtil.downLoad(fileName,resp,uploadProperties);
    }
}
