package com.ht.common.request.general;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-06 14:55
 **/
public class GeneralAddRequest {

    /**
     * 是否允许拷贝 0禁止 1允许
     */
    @NotNull(message = "拷贝参数不能为空")
    @Range(max = 1,message = "字段只允许0,1")
    private Integer copyState;

    /**
     * 是否允许拖拽 0禁止 1允许
     */
    @NotNull(message = "拖拽参数不能为空")
    @Range(max = 1,message = "字段只允许0,1")
    private Integer dragState;

    /**
     * 是否允许打印 0禁止 1允许
     */
    @NotNull(message = "打印参数不能为空")
    @Range(max = 1,message = "字段只允许0,1")
    private Integer printState;

    /**
     * 是否允许下载 0禁止 1允许
     */
    @NotNull(message = "下载参数不能为空")
    @Range(max = 1,message = "字段只允许0,1")
    private Integer downloadState;


    /**
     * 是否允许截屏  0禁止 1允许
     */
    private Integer screenshotState;

    /**
     * 缓存  0禁止 1允许
     */
    @NotNull(message = "缓存参数不能为空")
    @Range(max = 1,message = "字段只允许0,1")
    private Integer cacheState;

    /**
     * 完整性校验  0禁止 1允许
     */
    @NotNull(message = "完整性校验参数不能为空")
    @Range(max = 1,message = "字段只允许0,1")
    private Integer integrityState;

    public Integer getCopyState() {
        return copyState;
    }

    public void setCopyState(Integer copyState) {
        this.copyState = copyState;
    }

    public Integer getDragState() {
        return dragState;
    }

    public void setDragState(Integer dragState) {
        this.dragState = dragState;
    }

    public Integer getPrintState() {
        return printState;
    }

    public void setPrintState(Integer printState) {
        this.printState = printState;
    }

    public Integer getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(Integer downloadState) {
        this.downloadState = downloadState;
    }


    public Integer getScreenshotState() {
        return screenshotState;
    }

    public void setScreenshotState(Integer screenshotState) {
        this.screenshotState = screenshotState;
    }

    public Integer getCacheState() {
        return cacheState;
    }

    public void setCacheState(Integer cacheState) {
        this.cacheState = cacheState;
    }

    public Integer getIntegrityState() {
        return integrityState;
    }

    public void setIntegrityState(Integer integrityState) {
        this.integrityState = integrityState;
    }
}
