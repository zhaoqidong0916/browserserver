package com.ht.common.request.upgrade.cert.upgrade;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-23 09:59
 **/
public class CertUpgradeAddRequest {

    /**
     * 证书升级包名
     */
    @NotEmpty(message = "包名不能为空")
    private String certName;

    /**
     * 证书升级包描述
     */
    @NotEmpty(message = "描述不能为空")
    private String certDescribe;

    /**
     * 证书下载地址
     */
    @NotEmpty(message = "downloadUrl不能为空")
    private String downloadUrl;

    /**
     * 文件校验
     */
    @NotEmpty(message = "md5不能为空")
    private String md5;


    /**
     * 证书分类标记
     */
    @NotEmpty(message = "分类标记不能为空")
    private String certType;

    /**
     * 证书分类对应的名称
     */
    @NotEmpty(message = "分类标记名称不能为空")
    private String certTypeName;


    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getCertDescribe() {
        return certDescribe;
    }

    public void setCertDescribe(String certDescribe) {
        this.certDescribe = certDescribe;
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

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertTypeName() {
        return certTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        this.certTypeName = certTypeName;
    }
}
