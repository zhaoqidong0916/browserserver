package com.ht.common.request.compatible;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description: 更新浏览器兼容配置请求参数
 * @author: yaKun.shi
 * @create: 2020-03-06 14:24
 **/
public class CompatibleUpdateRequest {

    @NotEmpty(message ="id不能为空")
    private String id;

    /**
     * url或者域名
     */
    private String url;

    /**
     * 浏览器内核版本
     */
    @NotEmpty(message = "内核版本不能为空")
    private String kernel;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKernel() {
        return kernel;
    }

    public void setKernel(String kernel) {
        this.kernel = kernel;
    }
}
