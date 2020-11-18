package com.ht.common.request.safe.visit;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-09 14:44
 **/
public class VisitAddRequest {
    /**
     * 名单url
     */
    @NotEmpty(message = "名单url不能为空")
    private String forbiddenUrl;

    /**
     * url描述
     */
    private String urlDescription;

    public String getForbiddenUrl() {
        return forbiddenUrl;
    }

    public void setForbiddenUrl(String forbiddenUrl) {
        this.forbiddenUrl = forbiddenUrl;
    }

    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        this.urlDescription = urlDescription;
    }
}
