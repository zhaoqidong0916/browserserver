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
 * @since 2020-03-09
 */
public class BrowserVisitSafe extends Model<BrowserVisitSafe> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 黑名单url
     */
    @TableField("FORBIDDEN_URL")
    private String forbiddenUrl;

    /**
     * url描述
     */
    @TableField("URL_DESCRIPTION")
    private String urlDescription;

    /**
     * 0白名单  1黑名单
     */
    @TableField("STATE")
    private Integer state;

    /**
     * 审核状态 0：未审核，1：通过，2：拒绝
     */
    @TableField("REVIEW_STATUS")
    private Integer reviewStatus;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForbiddenUrl() {
        return forbiddenUrl;
    }

    public void setForbiddenUrl(String forbiddenUrl) {
        this.forbiddenUrl = forbiddenUrl;
    }

    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        this.urlDescription = urlDescription;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
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
        return "BrowserVisitSafe{" +
        ", id=" + id +
        ", forbiddenUrl=" + forbiddenUrl +
        ", urlDescription=" + urlDescription +
        ", reviewStatus=" + reviewStatus +
        ", checkStatus=" + checkStatus +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
