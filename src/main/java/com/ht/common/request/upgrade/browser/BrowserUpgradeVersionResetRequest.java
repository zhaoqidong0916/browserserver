package com.ht.common.request.upgrade.browser;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-17 15:39
 **/
public class BrowserUpgradeVersionResetRequest {


    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 升级包分类   1win,2兆芯,3兆芯,4arm,5arm,6龙芯,7龙芯
     */
    @NotNull(message = "浏览器类型不能为空")
    @Range(min = 1L,max = 7L,message = "browserType(1-7)")
    private Integer browserType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBrowserType() {
        return browserType;
    }

    public void setBrowserType(Integer browserType) {
        this.browserType = browserType;
    }
}
