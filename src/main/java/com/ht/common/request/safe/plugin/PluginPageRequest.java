package com.ht.common.request.safe.plugin;

import com.ht.common.page.PageRequest;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-09 15:34
 **/
public class PluginPageRequest extends PageRequest {

    /**
     * 控件名
     */
    private String pluginName;

    /**
     * 控件类型标识
     */
    private String pluginType;

    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginType() {
        return pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }
}
