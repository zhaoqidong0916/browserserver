package com.ht.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

public class BrowserErrorLog extends Model<BrowserErrorLog> {

    private static final long serialVersionUID = 1L;

    @TableField("BROWSER_VERSION")
    private String browserVersion;

    @TableField("CHECK_STATUS")
    private Integer checkStatus;

    @TableField("CONTENT")
    private String content;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("DAY")
    private Integer day;

    @TableField("ID")
    private String id;

    @TableField("IP_ADDRESS")
    private String ipAddress;

    @TableField("MAC")
    private String mac;

    @TableField("MONTH")
    private Integer month;

    @TableField("OS_VERSION")
    private String osVersion;

    @TableField("REMARK")
    private String remark;

    @TableField("TITLE")
    private String title;

    @TableField("TYPE_ID")
    private Integer typeId;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("URL")
    private String url;

    @TableField("USE_TIME")
    private String useTime;

    @TableField("YEAR")
    private Integer year;


    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
        return null;
    }

    @Override
    public String toString() {
        return "BrowserErrorLog{" +
                "browserVersion=" + browserVersion +
                ", checkStatus=" + checkStatus +
                ", content=" + content +
                ", createTime=" + createTime +
                ", day=" + day +
                ", id=" + id +
                ", ipAddress=" + ipAddress +
                ", mac=" + mac +
                ", month=" + month +
                ", osVersion=" + osVersion +
                ", remark=" + remark +
                ", title=" + title +
                ", typeId=" + typeId +
                ", updateTime=" + updateTime +
                ", url=" + url +
                ", useTime=" + useTime +
                ", year=" + year +
                "}";
    }
}