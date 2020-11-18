package com.ht.common.request.system.resources;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-24 10:32
 **/
public class ResourcesEnableRequest {

    @NotEmpty(message = "id不能为空")
    private String id;

    @NotNull(message = "sort不能为空")
    @Range(max = 1,message = "sort格式不正确")
    private Integer checkStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }
}
