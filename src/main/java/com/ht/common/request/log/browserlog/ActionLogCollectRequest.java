package com.ht.common.request.log.browserlog;

/**
 * @author: yang.yang
 * @email: Mryang905032390@163.com
 * @Date: 2020/9/22 16:35
 **/
public class ActionLogCollectRequest {
    /**
     * ip
     */
    private String ipAddress;

    /**
     * mac
     */
    private String mac;

    /**
     * 硬盘序列号
     */
    private String hardDiskSerial;

    /**
     * 操作系统版本
     */
    private String osVersionNum;

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
}
