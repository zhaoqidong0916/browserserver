package com.ht.common.request.upgrade.browser;

import com.ht.common.page.PageRequest;
import org.hibernate.validator.constraints.Range;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-12 14:16
 **/
public class BrowserUpgradePageRequest extends PageRequest {

    /**
     * 浏览器升级包名
     */
    private String browserName;


    /**
     * 版本
     */
    private String version;


    /**
     * 升级包分类   win,兆芯,arm,龙芯
     */

    private String browserTypeName;


    /**
     * 更新状态  1强制，0提示
     */
    @Range(max = 1L,message = "constraintStatus升级状态(1强制,0提示)")
    private Integer constraintStatus;


    private String createTime;


    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBrowserTypeName() {
        return browserTypeName;
    }

    public void setBrowserTypeName(String browserTypeName) {
        this.browserTypeName = browserTypeName;
    }

    public Integer getConstraintStatus() {
        return constraintStatus;
    }

    public void setConstraintStatus(Integer constraintStatus) {
        this.constraintStatus = constraintStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
