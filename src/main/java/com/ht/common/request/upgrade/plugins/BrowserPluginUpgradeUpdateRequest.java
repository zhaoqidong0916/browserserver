package com.ht.common.request.upgrade.plugins;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-13 11:52
 **/
public class BrowserPluginUpgradeUpdateRequest {


    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 控件升级包描述
     */
    @NotEmpty(message = "控件描述不能为空")
    private String pluginDescribe;

    @NotEmpty(message = "控件操作系统类型不能为空")
    private Integer type;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPluginDescribe() {
        return pluginDescribe;
    }

    public void setPluginDescribe(String pluginDescribe) {
        this.pluginDescribe = pluginDescribe;
    }

}
