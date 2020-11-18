package com.ht.common.request.application.resource;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-10 17:06
 **/
public class ApplicationResourceUpdateRequest {

    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 首页资源名
     */
    @NotEmpty(message = "资源名称不能为空")
    private String resourcesName;

    /**
     * 首页资源描述
     */
    @NotEmpty(message = "资源描述内容不能为空")
    private String resourcesDescribe;

    /**
     * 首页资源下载地址
    */
    private String downloadUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourcesName() {
        return resourcesName;
    }

    public void setResourcesName(String resourcesName) {
        this.resourcesName = resourcesName;
    }

    public String getResourcesDescribe() {
        return resourcesDescribe;
    }

    public void setResourcesDescribe(String resourcesDescribe) {
        this.resourcesDescribe = resourcesDescribe;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }


}
