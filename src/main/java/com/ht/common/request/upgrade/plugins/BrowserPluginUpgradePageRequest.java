package com.ht.common.request.upgrade.plugins;

import com.ht.common.page.PageRequest;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-13 11:52
 **/
public class BrowserPluginUpgradePageRequest extends PageRequest {
    /**
     * 浏览器升级包名
     */
    private String pluginName;

    private String pluginTypeName;

    private String createTime;

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }


    public String getPluginTypeName() {
        return pluginTypeName;
    }

    public void setPluginTypeName(String pluginTypeName) {
        this.pluginTypeName = pluginTypeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
