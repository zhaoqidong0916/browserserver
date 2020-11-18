package com.ht.common.request.system.resources;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-24 10:19
 **/
public class ResourcesAddRequest {


    /**
     * 前端资源url
     */
    @NotEmpty(message = "htmlUrl不能为空")
    private String htmlUrl;

    /**
     * 左侧树名
     */
    @NotEmpty(message = "urlName不能为空")
    private String urlName;

    /**
     * 父类名
     */
    @NotEmpty(message = "parentName不能为空")
    private String parentName;

    /**
     * 父级排序  数值越大越靠后
     */
    @NotNull(message = "sort不能为空")
    @Range(max = 50,message = "sort格式不正确")
    private Integer sort;

    /**
     * 资源二级排序 数值越大越靠后
     */
    @NotNull(message = "secondSort不能为空")
    @Range(max = 50,message = "secondSort格式不正确")
    private Integer secondSort;

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSecondSort() {
        return secondSort;
    }

    public void setSecondSort(Integer secondSort) {
        this.secondSort = secondSort;
    }
}
