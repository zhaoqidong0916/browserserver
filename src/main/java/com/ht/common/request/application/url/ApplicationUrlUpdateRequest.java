package com.ht.common.request.application.url;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-11 13:38
 **/
public class ApplicationUrlUpdateRequest {

    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 首页链接名
     */
    @NotEmpty(message = "链接名称不能为空")
    private String urlName;

    /**
     * 首页链接描述
     */
    @NotEmpty(message = "链接描述不能为空")
    private String urlDescribe;

    /**
     * 首页链接地址
     */
    private String urlAddress;


    /**
     * 首页图标下载地址
     */
    private String iconUrl;


    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getUrlDescribe() {
        return urlDescribe;
    }

    public void setUrlDescribe(String urlDescribe) {
        this.urlDescribe = urlDescribe;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }
}
