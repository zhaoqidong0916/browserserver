package com.ht.common.request.safe.visit;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-26 09:57
 **/
public class VisitStateEditRequest {
    @NotEmpty(message = "id不能为空")
    private String id;

    @NotNull(message = "state不能为空")
    @Range(max = 1)
    private Integer state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
