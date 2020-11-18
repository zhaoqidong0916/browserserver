package com.ht.common.request.upgrade.browser;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-12 14:15
 **/
public class BrowserUpgradeAddRequest {


    /**
     * 浏览器升级包名
     */
    @NotEmpty(message = "包名不能为空")
    private String browserName;

    /**
     * 浏览器升级包描述
     */
    @NotEmpty(message = "描述不能为空")
    private String browserDescribe;

    /**
     * 浏览器升级包服务器地址
     */
    @NotEmpty(message = "下载地址不能为空")
    private String downloadUrl;

    /**
     * 文件校验
     */
    @NotEmpty(message = "md5不能为空")
    private String md5;


    /**
     * 更新状态  1强制，0提示
     */
    @NotNull(message = "更新状态不能为空")
    @Range(max = 1L,message = "更新状态升级状态(1强制,0提示)不能为空")
    private Integer constraintStatus;



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

    public Integer getConstraintStatus() {
        return constraintStatus;
    }

    public void setConstraintStatus(Integer constraintStatus) {
        this.constraintStatus = constraintStatus;
    }
}
