package com.ht.common.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * @author yang.yang
 * @since 2020-09-22
 */
public class BrowserJsErrorLog extends Model<BrowserJsErrorLog> {
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
     * 错误信息内容
     */
    @TableField("CONTENT")
    private String content;

    /**
     * 行号
     */
    @TableField("ROW_NUM")
    private String rowNum;

    /**
     * js文件名
     */
    @TableField("FILE_NAME")
    private String fileName;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
