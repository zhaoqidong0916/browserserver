package com.ht.common.request.system.role;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-26 15:51
 **/
public class RoleResourceAddRequest {

    @NotEmpty(message = "角色id不能为空")
    private String roleId;
    @NotEmpty(message = "角色名不能为空")
    private String roleName;

    private List<ResourceParam> params;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<ResourceParam> getParams() {
        return params;
    }

    public void setParams(List<ResourceParam> params) {
        this.params = params;
    }
}
