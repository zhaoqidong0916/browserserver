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
public class AuthResources extends Model<AuthResources> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 前端资源url
     */
    @TableField("HTML_URL")
    private String htmlUrl;

    /**
     * 左侧树名
     */
    @TableField("URL_NAME")
    private String urlName;

    /**
     * 父类名
     */
    @TableField("PARENT_NAME")
    private String parentName;

    /**
     * 图标标志
     */
    @TableField("ICON_TYPE")
    private String iconType;

    /**
     * 父级排序  数值越大越靠后
     */
    @TableField("SORT")
    private Integer sort;

    /**
     * 资源二级排序 数值越大越靠后
     */
    @TableField("SECOND_SORT")
    private Integer secondSort;


    @TableField("REMARK")
    private String remark;

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


    @TableField(exist = false)
    private Integer selectEd;

    public Integer getSelectEd() {
        return selectEd;
    }

    public void setSelectEd(Integer selectEd) {
        this.selectEd = selectEd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSecondSort() {
        return secondSort;
    }

    public void setSecondSort(Integer secondSort) {
        this.secondSort = secondSort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AuthResources{" +
        ", id=" + id +
        ", htmlUrl=" + htmlUrl +
        ", urlName=" + urlName +
        ", parentName=" + parentName +
        ", iconType=" + iconType +
        ", sort=" + sort +
        ", secondSort=" + secondSort +
        ", createTime=" + createTime +
        ", remark=" + remark +
        ", checkStatus=" + checkStatus +
        ", updateTime=" + updateTime +
        "}";
    }
}
