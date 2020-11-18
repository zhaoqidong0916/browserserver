package com.ht.common.request.compatible;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description: 添加浏览器兼容配置请求参数
 * @author: yaKun.shi
 * @create: 2020-03-06 14:24
 **/
public class CompatibleAddRequest {

    /**
     * url或者域名
     */
    @NotEmpty(message ="url或者域名不能为空")
    private String url;

    /**
     * 浏览器内核版本
     */
    @NotEmpty(message = "内核版本不能为空")
    private String kernel;

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
