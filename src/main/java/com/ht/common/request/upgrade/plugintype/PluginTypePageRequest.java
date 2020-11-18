package com.ht.common.request.upgrade.plugintype;

import com.ht.common.page.PageRequest;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-16 11:32
 **/
public class PluginTypePageRequest extends PageRequest {

    /**
     * 控件标识名
     */
    private String pluginTypeName;

    public String getPluginTypeName() {
        return pluginTypeName;
    }

    public void setPluginTypeName(String pluginTypeName) {
        this.pluginTypeName = pluginTypeName;
    }
}
