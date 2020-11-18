package com.ht.common.request.application.url;

import com.ht.common.page.PageRequest;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-11 13:39
 **/
public class ApplicationUrlPageRequest extends PageRequest {

    /**
     * 首页链接名
     */
    private String urlName;

    /**
     * 首页链接地址
     */
    private String urlAddress;

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }
}
