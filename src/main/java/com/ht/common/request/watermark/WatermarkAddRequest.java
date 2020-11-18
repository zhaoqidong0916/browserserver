package com.ht.common.request.watermark;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-06 15:45
 **/
public class WatermarkAddRequest {

    /**
     * 水印名称
     */
    @NotEmpty(message = "水印名称不能为空")
    private String watermarkName;

    /**
     * 图片透明度
     */
    @NotEmpty(message = " 图片透明度不能为空")
    private Integer transparency;

    public String getWatermarkName() {
        return watermarkName;
    }

    public void setWatermarkName(String watermarkName) {
        this.watermarkName = watermarkName;
    }

    public Integer getTransparency() {
        return transparency;
    }

    public void setTransparency(Integer transparency) {
        this.transparency = transparency;
    }
}
