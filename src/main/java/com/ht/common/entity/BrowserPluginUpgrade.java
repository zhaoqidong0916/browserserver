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
public class BrowserPluginUpgrade extends Model<BrowserPluginUpgrade> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 浏览器升级包名
     */
    @TableField("PLUGIN_NAME")
    private String pluginName;

    /**
     * 浏览器升级包描述
     */
    @TableField("PLUGIN_DESCRIBE")
    private String pluginDescribe;

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
     * 系统版本
     */
    @TableField("TYPE")
    private Integer type;

    /**
     * 升级包分类   1win,2兆芯,3兆芯,4arm,5arm,6龙芯,7龙芯
     */
    @TableField("PLUGIN_TYPE")
    private String pluginType;

    /**
     * 升级包分类对应的名称
     */
    @TableField("PLUGIN_TYPE_NAME")
    private String pluginTypeName;

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

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginDescribe() {
        return pluginDescribe;
    }

    public void setPluginDescribe(String pluginDescribe) {
        this.pluginDescribe = pluginDescribe;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPluginType() {
        return pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    public String getPluginTypeName() {
        return pluginTypeName;
    }

    public void setPluginTypeName(String pluginTypeName) {
        this.pluginTypeName = pluginTypeName;
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
        return "BrowserPluginUpgrade{" +
        ", id=" + id +
        ", pluginName=" + pluginName +
        ", pluginDescribe=" + pluginDescribe +
        ", downloadUrl=" + downloadUrl +
        ", md5=" + md5 +
        ", version=" + version +
        ", type=" + type +
        ", plugintype=" + pluginType +
        ", pluginTypeName=" + pluginTypeName +
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
