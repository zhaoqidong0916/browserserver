package com.ht.common.request.application.resource;

import com.ht.common.page.PageRequest;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-10 17:06
 **/
public class ApplicationResourcePageRequest extends PageRequest {

    /**
     * 首页资源名
     */
    private String resourcesName;

    /**
     * 首页资源分类   1文本,2图片,3视频
     */
    private Integer resourcesType;

    public String getResourcesName() {
        return resourcesName;
    }

    public void setResourcesName(String resourcesName) {
        this.resourcesName = resourcesName;
    }

    public Integer getResourcesType() {
        return resourcesType;
    }

    public void setResourcesType(Integer resourcesType) {
        this.resourcesType = resourcesType;
    }
}
