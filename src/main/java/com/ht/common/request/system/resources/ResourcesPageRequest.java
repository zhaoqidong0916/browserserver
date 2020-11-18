package com.ht.common.request.system.resources;

import com.ht.common.page.PageRequest;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-24 10:20
 **/
public class ResourcesPageRequest extends PageRequest {

    /**
     * 左侧树名
     */
    private String urlName;

    /**
     * 父类名
     */
    private String parentName;


    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
