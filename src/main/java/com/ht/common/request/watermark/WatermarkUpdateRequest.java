package com.ht.common.request.watermark;


import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-06 15:45
 **/
public class WatermarkUpdateRequest {

    @NotEmpty(message = "id不能为空")
    private String id;
    /**
     * 水印名称
     */
    @NotEmpty(message = "watermarkName不能为空")
    private String watermarkName;

    /**
     * 图片透明度
     */
    @NotNull(message = "transparency不能为空")
    @Range(min = 0,max = 100,message = "透明度为0-100")
    private Integer transparency;


    private String downloadUrl;


    /**
     * 是否允许加水印 0禁止 1允许
     */
    @NotNull(message = "watermarkState不能为空")
    private Integer watermarkState;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getWatermarkState() {
        return watermarkState;
    }

    public void setWatermarkState(Integer watermarkState) {
        this.watermarkState = watermarkState;
    }
}
