package com.ht.common.request.system.role;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-26 15:20
 **/
public class RolesAddRequest {

    /**
     * 角色名
     */
    @NotEmpty(message = "角色名不能为空")
    private String roleName;

    /**
     * 角色描述
     */
    @NotEmpty(message = "角色描述不能为空")
    private String roleDescription;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
