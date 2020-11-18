package com.ht.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-06
 */
public class BrowserGeneralConfig extends Model<BrowserGeneralConfig> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 浏览器默认打开地址
     */
    @TableField("INDEX_PAGE")
    private String indexPage;

    /**
     * 是否允许拷贝 0禁止 1允许
     */
    @TableField("COPY_STATE")
    private Integer copyState;

    /**
     * 是否允许拖拽 0禁止 1允许
     */
    @TableField("DRAG_STATE")
    private Integer dragState;

    /**
     * 是否允许打印 0禁止 1允许
     */
    @TableField("PRINT_STATE")
    private Integer printState;

    /**
     * 是否允许下载 0禁止 1允许
     */
    @TableField("DOWNLOAD_STATE")
    private Integer downloadState;

    /**
     * 是否允许截屏  0禁止 1允许
     */
    @TableField("SCREENSHOT_STATE")
    private Integer screenshotState;

    /**
     * 缓存  0禁止 1允许
     */
    @TableField("CACHE_STATE")
    private Integer cacheState;

    /**
     * 完整性校验  0禁止 1允许
     */
    @TableField("INTEGRITY_STATE")
    private Integer integrityState;

    /**
     * 隐私设置 0禁止 1允许
     */
    @TableField("PRIVATE_STATE")
    private Integer privateState;

    /**
     * 审核状态 0：未审核，1：通过，2：拒绝
     */
    @TableField("REVIEW_STATUS")
    private Integer reviewStatus;

    /**
     * 状态 0正常，1禁用，2删除
     */
    @TableField("CHECK_STATUS")
    private Integer checkStatus;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 创建者
     */
    @TableField("CREATE_USER")
    private Date createUser;

    /**
     * 证书管控 0禁止 1允许
     */
    @TableField("CERT_STATE")
    private Integer certState;

    /**
     * 插件管控 0禁止所有 1允许所有 2部分允许
     */
    @TableField("PLUGIN_STATE")
    private Integer pluginState;

    /**
     * 白名单
     */
    @TableField("WHITE_LIST_STATE")
    private String whiteListState;

    /**
     * 是否允许加水印 0禁止 1允许
     */
    @TableField("WATERMARK_STATE")
    private Integer watermarkState;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(String indexPage) {
        this.indexPage = indexPage;
    }

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

    public Integer getPrivateState() {
        return privateState;
    }

    public void setPrivateState(Integer privateState) {
        this.privateState = privateState;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Date createUser) {
        this.createUser = createUser;
    }

    public Integer getCertState() {
        return certState;
    }

    public void setCertState(Integer certState) {
        this.certState = certState;
    }

    public Integer getPluginState() {
        return pluginState;
    }

    public void setPluginState(Integer pluginState) {
        this.pluginState = pluginState;
    }

    public String getWhiteListState() {
        return whiteListState;
    }

    public void setWhiteListState(String whiteListState) {
        this.whiteListState = whiteListState;
    }

    public Integer getWatermarkState() {
        return watermarkState;
    }

    public void setWatermarkState(Integer watermarkState) {
        this.watermarkState = watermarkState;
    }

    @Override
    public String toString() {
        return "BrowserGeneralConfig{" +
                "id='" + id + '\'' +
                ", indexPage='" + indexPage + '\'' +
                ", copyState=" + copyState +
                ", dragState=" + dragState +
                ", printState=" + printState +
                ", downloadState=" + downloadState +
                ", screenshotState=" + screenshotState +
                ", cacheState=" + cacheState +
                ", integrityState=" + integrityState +
                ", privateState=" + privateState +
                ", reviewStatus=" + reviewStatus +
                ", checkStatus=" + checkStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createUser=" + createUser +
                ", certState=" + certState +
                ", pluginState=" + pluginState +
                ", whiteListState='" + whiteListState + '\'' +
                ", watermarkState=" + watermarkState +
                '}';
    }
}
