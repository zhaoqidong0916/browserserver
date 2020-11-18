package com.ht.common.controller.back.homepage;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-04-27 14:45
 **/
public class ChartResult {

    private String name;
    private String url;
    private Integer value;
    private Integer key;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
