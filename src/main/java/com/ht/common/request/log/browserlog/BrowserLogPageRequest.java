package com.ht.common.request.log.browserlog;

import com.ht.common.page.PageRequest;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-10 09:57
 **/
public class BrowserLogPageRequest extends PageRequest {


    /**
     * ip
     */
    private String ipAddress;

    /**
     * mac
     */
    private String mac;

    /**
     * 客户端版本信息
     */
    private String browserVersion;

    /**
     * 1网页浏览,2浏览器启动,3尝试登录,4页面点击元素
     */
    private Integer typeId;

    /**
     * 标题
     */
    private String title;

    /**
     * url
     */
    private String url;


    private String createTime;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
