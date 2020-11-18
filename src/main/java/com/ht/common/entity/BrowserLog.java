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
public class BrowserLog extends Model<BrowserLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ID")
    private String id;

    /**
     * ip
     */
    @TableField("IP_ADDRESS")
    private String ipAddress;

    /**
     * mac
     */
    @TableField("MAC")
    private String mac;

    /**
     * 操作系统版本信息
     */
    @TableField("OS_VERSION")
    private String osVersion;

    /**
     * 客户端版本信息
     */
    @TableField("BROWSER_VERSION")
    private String browserVersion;

    /**
     * 1网页浏览,2浏览器启动,3尝试登录,4页面点击元素
     */
    @TableField("TYPE_ID")
    private Integer typeId;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * url
     */
    @TableField("URL")
    private String url;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

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

    @TableField(exist = false)
    private int hour;

    @TableField("MONTH")
    private int month;

    @TableField("DAY")
    private int day;

    @TableField("YEAR")
    private int year;


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
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

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        return "BrowserLog{" +
                ", id=" + id +
                ", ipAddress=" + ipAddress +
                ", mac=" + mac +
                ", osVersion=" + osVersion +
                ", browserVersion=" + browserVersion +
                ", typeId=" + typeId +
                ", title=" + title +
                ", url=" + url +
                ", remark=" + remark +
                ", checkStatus=" + checkStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
