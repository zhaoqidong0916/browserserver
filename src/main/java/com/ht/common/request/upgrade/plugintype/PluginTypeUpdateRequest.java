package com.ht.common.request.upgrade.plugintype;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-16 11:32
 **/
public class PluginTypeUpdateRequest {

    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 控件标识名
     */
    @NotEmpty(message = "控件标识名不能为空")
    private String pluginTypeName;

    /**
     * 控件类型标识
     */
    @NotEmpty(message = "控件类型标识不能为空")
    private String pluginType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPluginTypeName() {
        return pluginTypeName;
    }

    public void setPluginTypeName(String pluginTypeName) {
        this.pluginTypeName = pluginTypeName;
    }

    public String getPluginType() {
        return pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }
}
