package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.common.controller.browser.upgrade.request.RequestParam;
import com.ht.common.controller.browser.upgrade.request.UpgradeRequest;
import com.ht.common.dao.BrowserCertUpgradeMapper;
import com.ht.common.dao.BrowserPackageUpgradeMapper;
import com.ht.common.dao.BrowserPluginUpgradeMapper;
import com.ht.common.dao.BrowserUpgradeHistoryMapper;
import com.ht.common.entity.BrowserCertUpgrade;
import com.ht.common.entity.BrowserPackageUpgrade;
import com.ht.common.entity.BrowserPluginUpgrade;
import com.ht.common.entity.BrowserUpgradeHistory;
import com.ht.common.page.R;
import com.ht.common.service.IUpgradeService;
import com.ht.common.utils.CacheUtils;
import com.ht.common.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import static com.ht.common.constant.Constant.*;
import static com.ht.common.enums.ResponseEnum.CACHE_NOT_UPDATE;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-31 11:45
 **/
@Service
public class UpgradeServiceImpl implements IUpgradeService {


    private static Logger logger = LoggerFactory.getLogger(UpgradeServiceImpl.class);

    @Autowired
    private BrowserCertUpgradeMapper browserCertUpgradeMapper;

    @Autowired
    private BrowserPackageUpgradeMapper browserPackageUpgradeMapper;

    @Autowired
    private BrowserPluginUpgradeMapper browserPluginUpgradeMapper;
    @Autowired
    private BrowserUpgradeHistoryMapper browserUpgradeHistoryMapper;

    @Override
    public R browserUpgrade(String version, Integer systemType) {
        //不同的类型不同的key
        String key = BROWSER_UPGRADE + systemType;
        //从缓存中取数据
        R redisData = getBrowserCache(key, version);
        if (redisData != null) {
            insertHistory(BROWSER_COUNT_TYPE);
            return redisData;
        }
        QueryWrapper<BrowserPackageUpgrade> wrapper = new QueryWrapper<>();
        wrapper.select("DOWNLOAD_URL", "CONSTRAINT_STATUS", "VERSION").eq("HISTORY_STATUS", 0).
                eq("CHECK_STATUS", 0).eq("BROWSER_TYPE", systemType);
        //查询数据库中的需要升级的数据
        BrowserPackageUpgrade browserPackageUpgrade = browserPackageUpgradeMapper.selectOne(wrapper);

        //如果为空，或者版本号相同 不需要升级
        if (browserPackageUpgrade == null || version.equals(browserPackageUpgrade.getVersion())) {
            return R.result(CACHE_NOT_UPDATE);
        }

        insertHistory(BROWSER_COUNT_TYPE);
        //加入缓存中
        CacheUtils.put(key, browserPackageUpgrade);
        return R.success(browserPackageUpgrade);
    }

    @Override
    public R pluginUpgrade(UpgradeRequest request) {
        //不同的类型不同的key
        String key = PLUGIN_UPGRADE + request.getSystemType();
        List<RequestParam> updateParam = request.getUpdateParam();
        //从缓存中取数据
        R redisData = getCache(key, BrowserPluginUpgrade.class, updateParam);
        if (redisData != null) {
            logger.error("plugin-------------------------cache");
            insertHistory(PLUGIN_COUNT_TYPE);
            return redisData;
        }
        logger.error("plugin-----------------------sql");
        Integer systemType = request.getSystemType();
        //根据systemType查询数据库中的数据
        List<BrowserPluginUpgrade> browserPluginUpgrades = browserPluginUpgradeMapper.selectList(new QueryWrapper<BrowserPluginUpgrade>().
                select("PLUGIN_NAME", "PLUGIN_TYPE", "MD5", "DOWNLOAD_URL", "PLUGIN_TYPE_NAME").
                eq("TYPE", systemType).
                eq("CHECK_STATUS", 0));
        //如果数据库为空，直接返回不需要安装插件
        if (CollectionUtils.isEmpty(browserPluginUpgrades)) {
            return R.result(CACHE_NOT_UPDATE);
        }
        //使用set来存储,方便去重
        //处理数据库不为空，参数也不为空的情况
        Set<Object> resultSet = handle(updateParam, BrowserPluginUpgrade.class, browserPluginUpgrades);
        if (CollectionUtils.isEmpty(resultSet)) {
            return R.result(CACHE_NOT_UPDATE);
        }
        logger.error("plugin-----------------------insert");
        insertHistory(PLUGIN_COUNT_TYPE);
        //加入缓存中
        CacheUtils.put(key, resultSet);
        return R.success(resultSet);
    }

    @Override
    public R certUpgrade(UpgradeRequest request) {
        //不同的类型不同的key
        String key = CERT_UPGRADE + request.getSystemType();
        List<RequestParam> updateParam = request.getUpdateParam();
        //从缓存中取数据
        R redisData = getCache(key, BrowserCertUpgrade.class, updateParam);
        if (redisData != null) {
            logger.error("cert----------------------------cache");
            insertHistory(CERT_COUNT_TYPE);
            return redisData;
        }
        logger.error("cert------------------------------sql");
        //根据systemType查询数据库中的数据
        List<BrowserCertUpgrade> browserCertUpgrades = browserCertUpgradeMapper.selectList(
                new QueryWrapper<BrowserCertUpgrade>().
                        select("CERT_NAME", "CERT_TYPE", "MD5", "DOWNLOAD_URL", "CERT_TYPE_NAME").
                        eq("TYPE", request.getSystemType()).
                        eq("CHECK_STATUS", 0));
        //如果数据库为空，直接返回不需要安装插件
        if (CollectionUtils.isEmpty(browserCertUpgrades)) {
            return R.result(CACHE_NOT_UPDATE);
        }
        //处理数据库不为空，参数也不为空的情况
        Set<Object> resultSet = handle(updateParam, BrowserCertUpgrade.class, browserCertUpgrades);
        if (CollectionUtils.isEmpty(resultSet)) {
            return R.result(CACHE_NOT_UPDATE);
        }
        logger.error("cert------------------------------insert");
        insertHistory(CERT_COUNT_TYPE);
        //加入缓存中
        CacheUtils.put(key, resultSet);
        return R.success(resultSet);
    }

    /**
     * @param updateParam
     * @param clazz
     * @param upgrades
     * @return
     */
    private Set<Object> handle(List<RequestParam> updateParam, Class<?> clazz, List<?> upgrades) {
        //使用set来存储,方便去重
        Set<Object> resultSet = new HashSet<>();
        for (int i = 0; i < upgrades.size(); i++) {
            String type = null;
            String md5 = null;
            Object data = null;
            if (clazz.equals(BrowserPluginUpgrade.class)) {
                BrowserPluginUpgrade pluginUpgrade = (BrowserPluginUpgrade) upgrades.get(i);
                type = pluginUpgrade.getPluginType();
                md5 = pluginUpgrade.getMd5();
                data = pluginUpgrade;
            }
            if (clazz.equals(BrowserCertUpgrade.class)) {
                BrowserCertUpgrade browserCertUpgrade = (BrowserCertUpgrade) upgrades.get(i);
                type = browserCertUpgrade.getCertType();
                md5 = browserCertUpgrade.getMd5();
                data = browserCertUpgrade;
            }
            for (int j = 0; j < updateParam.size(); j++) {
                RequestParam param = updateParam.get(j);
                String paramMd5 = param.getMd5();
                String paramCertType = param.getType();
                //如果传入的控件类型标记和数据库查询的一样,代表有相同类型的证书。
                //然后再比较md5,来判断是否为同一个文件，如果MD5不同说明需要返回,相同说明不需要返回
                if (paramCertType.equals(type)) {
                    //判断MD5
                    if (!paramMd5.equals(md5)) {
                        //md5不相同 放入要返回的集合中
                        resultSet.add(data);
                    }
                } else {
                    //控件类型不一致, 可能会出现重复数据问题,使用set去重
                    resultSet.add(data);
                }

            }
        }
        return resultSet;
    }


    /**
     * 从缓存中读取数据
     *
     * @return null代表要数据库, 其他直接返回
     */
    private R getCache(String key, Class clazz, List<RequestParam> updateParam) {
        //不存在key直接返回null
        Object cache = CacheUtils.get(key);
        if (cache == null) {
            return null;
        }
        Set<Object> handle = null;
        if (clazz.equals(BrowserPluginUpgrade.class)) {
            Set<BrowserPluginUpgrade> pluginUpgrade = (Set<BrowserPluginUpgrade>) cache;
            List<BrowserPluginUpgrade> pluginUpgradeList = Arrays.asList(pluginUpgrade.toArray(new BrowserPluginUpgrade[]{}));
            handle = handle(updateParam, clazz, pluginUpgradeList);
            if (CollectionUtils.isEmpty(handle)) {
                return null;
            }
        }
        if (clazz.equals(BrowserCertUpgrade.class)) {
            Set<BrowserCertUpgrade> browserCertUpgrade = (Set<BrowserCertUpgrade>) cache;
            List<BrowserCertUpgrade> browserCertUpgradeList = Arrays.asList(browserCertUpgrade.toArray(new BrowserCertUpgrade[]{}));
            handle = handle(updateParam, clazz, browserCertUpgradeList);
            if (CollectionUtils.isEmpty(handle)) {
                return null;
            }
        }
        return R.success(handle);
    }


    /**
     * 从缓存中读取数据
     *
     * @return null代表要数据库, 其他直接返回
     */
    private R getBrowserCache(String key, String version) {
        //不存在key直接返回null
        Object cache = CacheUtils.get(key);
        if (cache == null) {
            return null;
        }
        //版本号一致就直接返回不需要更新
        BrowserPackageUpgrade browserPackageUpgrade = (BrowserPackageUpgrade) cache;
        if (browserPackageUpgrade.getVersion().equals(version)) {
            return R.result(CACHE_NOT_UPDATE);
        }
        return R.success(cache);
    }

    /**
     * 存储更新的人数
     *
     * @param type
     */
    private void insertHistory(Integer type) {
        BrowserUpgradeHistory browserUpgradeHistory = new BrowserUpgradeHistory();
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        browserUpgradeHistory.setCreateTime(new Date());
        browserUpgradeHistory.setDay(day);
        browserUpgradeHistory.setYear(year);
        browserUpgradeHistory.setMonth(month);
        browserUpgradeHistory.setType(type);
        browserUpgradeHistory.setId(UUIDUtil.getUUID());
        browserUpgradeHistoryMapper.insert(browserUpgradeHistory);
    }

	@Override
	public boolean headList(HttpServletRequest ret) {
		Enumeration<String> headerNames = ret.getHeaderNames();
	    while (headerNames.hasMoreElements()){
	    
	      String name = (String)headerNames.nextElement();
	      
	      String value = ret.getHeader(name);
	      if ((name.equals("token")) && (value.equals("103702738237"))) {
	        return true;
	      }
	    }
	    return false;
	}
}
