package com.ht.common.request.compatible;

import com.ht.common.page.PageRequest;

/**
 * @description: 列表分页请求参数
 * @author: yaKun.shi
 * @create: 2020-03-06 14:25
 **/
public class CompatiblePageRequest extends PageRequest {


    private String url;

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
