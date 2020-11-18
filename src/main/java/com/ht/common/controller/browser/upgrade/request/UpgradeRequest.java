package com.ht.common.controller.browser.upgrade.request;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 证书和控件升级请求参数
 * @author: yaKun.shi
 * @create: 2020-03-16 15:02
 **/
public class UpgradeRequest {

    @NotNull(message = "systemType不能为空")
    private Integer systemType;

    private List<RequestParam> updateParam;


    public Integer getSystemType() {
        return systemType;
    }

    public void setSystemType(Integer systemType) {
        this.systemType = systemType;
    }

    public List<RequestParam> getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(List<RequestParam> updateParam) {
        this.updateParam = updateParam;
    }
}
