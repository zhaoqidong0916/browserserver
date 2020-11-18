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
 * @since 2020-03-23
 */
public class BrowserCertUpgrade extends Model<BrowserCertUpgrade> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 证书升级包名
     */
    @TableField("CERT_NAME")
    private String certName;

    /**
     * 证书升级包描述
     */
    @TableField("CERT_DESCRIBE")
    private String certDescribe;

    /**
     * 证书下载地址
     */
    @TableField("DOWNLOAD_URL")
    private String downloadUrl;

    /**
     * 文件校验
     */
    @TableField("MD5")
    private String md5;

    /**
     * 系统类型
     */
    @TableField("TYPE")
    private Integer type;

    /**
     * 证书分类标记
     */
    @TableField("CERT_TYPE")
    private String certType;

    /**
     * 证书分类对应的名称
     */
    @TableField("CERT_TYPE_NAME")
    private String certTypeName;

    /**
     * 更新状态  1强制，0提示
     */
    @TableField("CONSTRAINT_STATUS")
    private Integer constraintStatus;

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

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getCertDescribe() {
        return certDescribe;
    }

    public void setCertDescribe(String certDescribe) {
        this.certDescribe = certDescribe;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertTypeName() {
        return certTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        this.certTypeName = certTypeName;
    }

    public Integer getConstraintStatus() {
        return constraintStatus;
    }

    public void setConstraintStatus(Integer constraintStatus) {
        this.constraintStatus = constraintStatus;
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
        return "BrowserCertUpgrade{" +
        ", id=" + id +
        ", certName=" + certName +
        ", certDescribe=" + certDescribe +
        ", downloadUrl=" + downloadUrl +
        ", md5=" + md5 +
        ", type=" + type +
        ", certType=" + certType +
        ", certTypeName=" + certTypeName +
        ", constraintStatus=" + constraintStatus +
        ", reviewStatus=" + reviewStatus +
        ", review=" + review +
        ", checkStatus=" + checkStatus +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
