package com.ht.common.request.upgrade.plugintype;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-16 11:32
 **/
public class PluginTypeAddRequest {

    /**
     * 控件标识名
     */
    @NotEmpty(message = "控件标识名不能为空")
    private String pluginTypeName;


    public String getPluginTypeName() {
        return pluginTypeName;
    }

    public void setPluginTypeName(String pluginTypeName) {
        this.pluginTypeName = pluginTypeName;
    }
}
