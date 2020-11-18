package com.ht.common.request.upgrade.cert.type;

import com.ht.common.page.PageRequest;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-23 09:14
 **/
public class CertTypePageReqeust extends PageRequest {
    /**
     * 证书标识名
     */
    private String certTypeName;

    public String getCertTypeName() {
        return certTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        this.certTypeName = certTypeName;
    }
}
