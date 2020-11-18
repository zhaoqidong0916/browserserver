package com.ht.common.shiro.filter;

import com.ht.common.shiro.config.ICustomFilterChainDefinitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author yakun.shi
 * @Description
 * @Date 2019/7/11 15:39
 **/
@Component
public class CustomFilterChainDefinitions implements ICustomFilterChainDefinitions {

    private static Logger logger = LoggerFactory.getLogger(CustomFilterChainDefinitions.class);

    @Value("${upload.suffix-name}")
    private String name;

    /**
     * 初始化权限
     */
    @Override
    public Map<String, String> loadFilterChainDefinitions() {
        Map<String, String> filter = getAllowPermission();
        //拦截所有请求
        filter.put("/**", "authc");
//        filter.put("/**", "anon");
        return filter;
    }

    /**
     * 过虑链
     *
     * @return
     */
    private Map<String, String> getAllowPermission() {
        Map<String, String> filter = new LinkedHashMap<>();
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        if (name.length() > 1) {
              name = name.substring(0,name.length()-1);
        }
        // 放行后端接口
        filter.put("/userLogin", "anon");
        filter.put("/logout", "anon");
        filter.put("/download/**", "anon");
        filter.put("/", "anon");
        filter.put("/index", "anon");
        filter.put("/application/resourceList", "anon");
        filter.put("/application/urlList", "anon");
        filter.put("/browser/safeList", "anon");
        filter.put("/browser/infoCollect", "anon");
        filter.put("/browser/configList", "anon");
        filter.put("/upgrade/browser", "anon");
        filter.put("/upgrade/plugin", "anon");
        filter.put("/upgrade/cert", "anon");
        filter.put("/browserLog/errorCollect", "anon");
        filter.put("/browserLog/errorJsCollect", "anon");
        filter.put("/browser/actionLogCollect", "anon");
        //放行静态文件
        filter.put("/templates/**", "anon");
        filter.put("/static/**", "anon");
        filter.put("/css/**", "anon");
        filter.put("/images/**", "anon");
        filter.put("/js/**", "anon");
        filter.put("/public/**", "anon");
        filter.put("/views/**", "anon");
        filter.put("/login.html", "anon");
        filter.put("/home.html", "anon");
        //放行swagger
        filter.put("/swagger-ui.html", "anon");
        filter.put("/swagger-resources/**", "anon");
        filter.put("/v2/api-docs", "anon");
        filter.put("/webjars/springfox-swagger-ui/**", "anon");
        return filter;
    }
}
