package com.ht.common.controller.browser.safe.request;

/**
 * @author: yang.yang
 * @email: Mryang905032390@163.com
 * @Date: 2020/9/22 11:08
 **/
public class ErrorJsCollectRequest {
    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 错误信息内容
     */
    private String content;

    /**
     * 行号
     */
    private String rowNum;

    /**
     * js文件名
     */
    private String fileName;

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
}
