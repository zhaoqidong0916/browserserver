package com.ht.common.request.system.role;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-26 15:21
 **/
public class RolesEnableRequest {

    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 状态 0正常，1禁用
     */
    @NotNull(message = "checkStatus不能为空")
    @Range(max = 2)
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
