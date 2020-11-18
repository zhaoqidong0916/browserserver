package com.ht.common.request.upgrade.plugins;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-13 11:52
 **/
public class BrowserPluginUpgradeAddRequest {

    /**
     * 控件升级包名
     */
    @NotEmpty(message = "控件包名不能为空")
    private String pluginName;

    /**
     * 控件升级包描述
     */
    @NotEmpty(message = "控件描述不能为空")
    private String pluginDescribe;

    /**
     * 控件升级包服务器地址
     */
    @NotEmpty(message = "下载地址不能为空")
    private String downloadUrl;

    /**
     * 文件校验
     */
    @NotEmpty(message = "md5不能为空")
    private String md5;

    @NotEmpty(message = "pluginType不能为空")
    private String pluginType;
    @NotEmpty(message = "pluginTypeName不能为空")
    private String pluginTypeName;


    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginDescribe() {
        return pluginDescribe;
    }

    public void setPluginDescribe(String pluginDescribe) {
        this.pluginDescribe = pluginDescribe;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPluginType() {
        return pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    public String getPluginTypeName() {
        return pluginTypeName;
    }

    public void setPluginTypeName(String pluginTypeName) {
        this.pluginTypeName = pluginTypeName;
    }
}
