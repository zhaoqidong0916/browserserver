package com.ht.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
public class AuthRoleResources extends Model<AuthRoleResources> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 角色id
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 角色名
     */
    @TableField("ROLE_NAME")
    private String roleName;

    /**
     * 资源id
     */
    @TableField("RESOURCE_ID")
    private String resourceId;

    /**
     * 资源名
     */
    @TableField("RESOURCE_NAME")
    private String resourceName;

    /**
     * 状态 0正常，1禁用
     */
    @TableField("CHECK_STATUS")
    private Integer checkStatus;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;


    @TableField("REMARK")
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AuthRoleResources{" +
        ", id=" + id +
        ", roleId=" + roleId +
        ", roleName=" + roleName +
        ", resourceId=" + resourceId +
        ", resourceName=" + resourceName +
        ", checkStatus=" + checkStatus +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        ", remark=" + remark +
        "}";
    }
}
