package com.ht.common.request.upgrade.browser;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-12 14:16
 **/
public class BrowserUpgradeUpdateRequest {

    @NotEmpty(message = "id不能为空")
    private String id;


    /**
     * 更新状态  1强制，0提示
     */
    @NotNull(message = "更新状态不能为空")
    @Range(max = 1L,message = "更新状态升级状态(1强制,0提示)不能为空")
    private Integer constraintStatus;

    @NotNull(message = "浏览器类型不能为空")
    private Integer browserType;

    @NotEmpty(message = "描述不能为空")
    private String browserDescribe;

    public String getBrowserDescribe() {
        return browserDescribe;
    }

    public void setBrowserDescribe(String browserDescribe) {
        this.browserDescribe = browserDescribe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getConstraintStatus() {
        return constraintStatus;
    }

    public void setConstraintStatus(Integer constraintStatus) {
        this.constraintStatus = constraintStatus;
    }

    public Integer getBrowserType() {
        return browserType;
    }

    public void setBrowserType(Integer browserType) {
        this.browserType = browserType;
    }
}
