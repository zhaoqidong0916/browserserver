package com.ht.common.request.log.browserlog;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-10 16:02
 **/
public class InfoCollectRequest {

    @NotEmpty(message = "ip不能为空")
    private String ipAddress;

    /**
     * mac
     */
    @NotEmpty(message = "mac不能为空")
    private String mac;


    /**
     * 客户端版本信息
     */
    @NotEmpty(message = "客户端版本不能为空")
    private String browserVersion;

    /**
     * 1网页浏览,2浏览器启动,3尝试登录,4页面点击元素
     */
    private Integer typeId;

    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空")
    private String title;

    /**
     * url
     */
    @NotEmpty(message = "url不能为空")
    private String url;

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
}
