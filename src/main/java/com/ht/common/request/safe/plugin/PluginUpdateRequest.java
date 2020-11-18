package com.ht.common.request.safe.plugin;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-09 15:34
 **/
public class PluginUpdateRequest {

    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 控件名
     */
    @NotEmpty(message = "控件名称不能为空")
    private String pluginName;

    /**
     * 控件类型标识
     */
    private String pluginType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
