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
 * @since 2020-03-11
 */
public class BrowserPackageUpgrade extends Model<BrowserPackageUpgrade> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 浏览器升级包名
     */
    @TableField("BROWSER_NAME")
    private String browserName;

    /**
     * 浏览器升级包描述
     */
    @TableField("BROWSER_DESCRIBE")
    private String browserDescribe;

    /**
     * 浏览器升级包服务器地址
     */
    @TableField("DOWNLOAD_URL")
    private String downloadUrl;

    /**
     * 文件校验
     */
    @TableField("MD5")
    private String md5;

    /**
     * 版本
     */
    @TableField("VERSION")
    private String version;

    /**
     * 版本号标记
     */
    @TableField("VERSION_MARK")
    private Integer versionMark;

    /**
     * 升级包分类   1win,2兆芯,3兆芯,4arm,5arm,6龙芯,7龙芯
     */
    @TableField("BROWSER_TYPE")
    private Integer browserType;

    /**
     * 升级包分类对应的名称
     */
    @TableField("BROWSER_TYPE_NAME")
    private String browserTypeName;

    /**
     * 更新状态  1强制，0提示
     */
    @TableField("CONSTRAINT_STATUS")
    private Integer constraintStatus;

    /**
     * 客户端升级提示语
     */
    @TableField("HINT_VALUE")
    private String hintValue;

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
     * 是否为历史版本 0否，1是
     */
    @TableField("HISTORY_STATUS")
    private Integer historyStatus;
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

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserDescribe() {
        return browserDescribe;
    }

    public void setBrowserDescribe(String browserDescribe) {
        this.browserDescribe = browserDescribe;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getVersionMark() {
        return versionMark;
    }

    public void setVersionMark(Integer versionMark) {
        this.versionMark = versionMark;
    }

    public Integer getBrowserType() {
        return browserType;
    }

    public void setBrowserType(Integer browserType) {
        this.browserType = browserType;
    }

    public String getBrowserTypeName() {
        return browserTypeName;
    }

    public void setBrowserTypeName(String browserTypeName) {
        this.browserTypeName = browserTypeName;
    }

    public Integer getConstraintStatus() {
        return constraintStatus;
    }

    public void setConstraintStatus(Integer constraintStatus) {
        this.constraintStatus = constraintStatus;
    }

    public String getHintValue() {
        return hintValue;
    }

    public void setHintValue(String hintValue) {
        this.hintValue = hintValue;
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

    public Integer getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(Integer historyStatus) {
        this.historyStatus = historyStatus;
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
        return "BrowserPackageUpgrade{" +
        ", id=" + id +
        ", browserName=" + browserName +
        ", browserDescribe=" + browserDescribe +
        ", downloadUrl=" + downloadUrl +
        ", md5=" + md5 +
        ", version=" + version +
        ", versionMark=" + versionMark +
        ", browserType=" + browserType +
        ", browserTypeName=" + browserTypeName +
        ", constraintStatus=" + constraintStatus +
        ", hintValue=" + hintValue +
        ", reviewStatus=" + reviewStatus +
        ", review=" + review +
        ", checkStatus=" + checkStatus +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
