package com.ht.common.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @author yang.yang
 * @since 2020-09-22
 */
public class BrowserActionLog extends Model<BrowserActionLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("ID")
    private String id;

    /**
     * ip地址
     */
    @TableField("IP_ADDRESS")
    private String ipAddress;

    /**
     * mac地址
     */
    @TableField("MAC")
    private String mac;

    /**
     * 硬盘序列号
     */
    @TableField("HARD_Disk_SERIAL")
    private String hardDiskSerial;

    /**
     * 操作系统版本
     */
    @TableField("OS_VERSION_NUM")
    private String osVersionNum;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_TIME")
    private Date updateTime;

    /**
     * 备用字段
     */
    @TableField("REMARK")
    private String remark;


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

    public String getHardDiskSerial() {
        return hardDiskSerial;
    }

    public void setHardDiskSerial(String hardDiskSerial) {
        this.hardDiskSerial = hardDiskSerial;
    }

    public String getOsVersionNum() {
        return osVersionNum;
    }

    public void setOsVersionNum(String osVersionNum) {
        this.osVersionNum = osVersionNum;
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
}
