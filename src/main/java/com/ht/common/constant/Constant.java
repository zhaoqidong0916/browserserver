package com.ht.common.constant;

/**
 * @description: 常量定义
 * @author: yaKun.shi
 * @create: 2020-03-05 13:44
 **/
public interface Constant {

    /**
     * 更新浏览器升级缓存key
     */
    String BROWSER_UPGRADE = "browserUpgrade:";

    /**
     * 更新控件升级缓存key
     */
    String PLUGIN_UPGRADE = "pluginUpgrade:";

    /**
     * 更新证书升级缓存key
     */
    String CERT_UPGRADE = "certUpgrade:";

    /**
     * 首页资源缓存key
     */
    String APPLICATION = "application";

    /**
     * 首页链接地址缓存key
     */
    String APPLICATION_URL = "applicationUrl";

    /**
     * 安全缓存key
     */
    String SAFE = "safe";
    /**
     * 策略缓存key
     */
    String STRATEGY = "strategy";

    /**
     * 统计登录次数标记
     */
    Integer LOGIN_COUNT_TYPE = 0;
    /**
     * 统计证书升级次数标记
     */
    Integer CERT_COUNT_TYPE = 1;
    /**
     * 统计控件升级次数标记
     */
    Integer PLUGIN_COUNT_TYPE = 2;
    /**
     * 统计客户端升级次数标记
     */
    Integer BROWSER_COUNT_TYPE = 3;

    /**
     * 统计客户端活跃度 按日标记
     */
    Integer BROWSER_DAY = 0;
    /**
     * 统计客户端活跃度 按月标记
     */
    Integer BROWSER_MONTH = 1;
    /**
     * 统计客户端活跃度 按年标记
     */
    Integer BROWSER_YEAR = 2;


    /**
     * 统计首页菜单访问 柱状图标记
     */
    Integer HISTOGRAM_TYPE = 0;

    /**
     * 统计首页菜单访问 饼图标记
     */
    Integer PIE_CHART_TYPE = 1;
}
