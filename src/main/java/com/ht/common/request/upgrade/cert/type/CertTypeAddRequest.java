package com.ht.common.request.upgrade.cert.type;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-23 09:14
 **/
public class CertTypeAddRequest {

    /**
      * 证书标识名
     */
    @NotEmpty(message = "证书标识名称不能为空")
    private String certTypeName;

    public String getCertTypeName() {
        return certTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        this.certTypeName = certTypeName;
    }
}
