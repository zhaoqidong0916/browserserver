package com.ht.common.request.upgrade.cert.upgrade;

import com.ht.common.page.PageRequest;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-23 10:00
 **/
public class CertUpgradePageRequest extends PageRequest {

    /**
     * 证书升级包名
     */
    private String certName;

    /**
     * 证书分类对应的名称
     */
    private String certTypeName;

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getCertTypeName() {
        return certTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        this.certTypeName = certTypeName;
    }
}
