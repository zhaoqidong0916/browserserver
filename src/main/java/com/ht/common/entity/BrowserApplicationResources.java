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
 * @since 2020-03-10
 */
public class BrowserApplicationResources extends Model<BrowserApplicationResources> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 首页资源名
     */
    @TableField("RESOURCES_NAME")
    private String resourcesName;

    /**
     * 首页资源描述
     */
    @TableField("RESOURCES_DESCRIBE")
    private String resourcesDescribe;

    /**
     * 首页资源下载地址
     */
    @TableField("DOWNLOAD_URL")
    private String downloadUrl;

    /**
     * 首页资源分类   1文本,2图片,3视频
     */
    @TableField("RESOURCES_TYPE")
    private Integer resourcesType;

    /**
     * 首页资源分类对应的名称
     */
    @TableField("RESOURCES_TYPE_NAME")
    private String resourcesTypeName;

    /**
     * 审核状态 0：未审核，1：通过，2：拒绝
     */
    @TableField("REVIEW_STATUS")
    private Integer reviewStatus;

    /**
     * 审核意见
     */
    @TableField("REVIEW")
    private String review;

    /**
     * 状态 0正常，1禁用，2删除
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

    public String getResourcesName() {
        return resourcesName;
    }

    public void setResourcesName(String resourcesName) {
        this.resourcesName = resourcesName;
    }

    public String getResourcesDescribe() {
        return resourcesDescribe;
    }

    public void setResourcesDescribe(String resourcesDescribe) {
        this.resourcesDescribe = resourcesDescribe;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getResourcesType() {
        return resourcesType;
    }

    public void setResourcesType(Integer resourcesType) {
        this.resourcesType = resourcesType;
    }

    public String getResourcesTypeName() {
        return resourcesTypeName;
    }

    public void setResourcesTypeName(String resourcesTypeName) {
        this.resourcesTypeName = resourcesTypeName;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
        return "BrowserApplicationResources{" +
        ", id=" + id +
        ", resourcesName=" + resourcesName +
        ", resourcesDescribe=" + resourcesDescribe +
        ", downloadUrl=" + downloadUrl +
        ", resourcesType=" + resourcesType +
        ", resourcesTypeName=" + resourcesTypeName +
        ", reviewStatus=" + reviewStatus +
        ", review=" + review +
        ", checkStatus=" + checkStatus +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
