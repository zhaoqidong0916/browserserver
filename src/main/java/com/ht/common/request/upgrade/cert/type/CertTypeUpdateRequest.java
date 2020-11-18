package com.ht.common.request.upgrade.cert.type;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-23 09:14
 **/
public class CertTypeUpdateRequest {

    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 证书标识名
     */
    @NotEmpty(message = "证书标识名称不能为空")
    private String certTypeName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCertTypeName() {
        return certTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        this.certTypeName = certTypeName;
    }
}
