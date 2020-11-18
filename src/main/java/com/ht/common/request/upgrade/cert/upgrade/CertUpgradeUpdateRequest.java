package com.ht.common.request.upgrade.cert.upgrade;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-23 09:59
 **/
public class CertUpgradeUpdateRequest {

    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 证书升级包描述
     */
    @NotEmpty(message = "升级包描述不能为空")
    private String certDescribe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCertDescribe() {
        return certDescribe;
    }

    public void setCertDescribe(String certDescribe) {
        this.certDescribe = certDescribe;
    }
}
