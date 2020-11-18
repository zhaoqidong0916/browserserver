package com.ht.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.common.dao.*;
import com.ht.common.entity.*;
import com.ht.common.page.R;
import com.ht.common.request.log.browserlog.ActionLogCollectRequest;
import com.ht.common.request.log.browserlog.InfoCollectRequest;
import com.ht.common.service.ISafeService;
import com.ht.common.utils.CacheUtils;
import com.ht.common.utils.ToolMD5;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import static com.ht.common.constant.Constant.SAFE;
import static com.ht.common.constant.Constant.STRATEGY;
import static com.ht.common.enums.ResponseEnum.CACHE_NOT_UPDATE;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-10 11:46
 **/
@Service
public class SafeServiceImpl implements ISafeService {
    @Autowired
    private BrowserVisitSafeMapper browserVisitSafeMapper;
    @Autowired
    private BrowserPluginSafeMapper browserPluginSafeMapper;
    @Autowired
    private BrowserCompatibleConfigMapper browserCompatibleConfigMapper;
    @Autowired
    private BrowserGeneralConfigMapper browserGeneralConfigMapper;
    @Autowired
    private BrowserWatermarkConfigMapper browserWatermarkConfigMapper;
    @Autowired
    private BrowserLogMapper browserLogMapper;
    @Autowired
    private BrowserActionLogMapper browserActionLogMapper;

    @Override
    public R safeList(String md5) {
        //先从redis缓存中取数据如果有数据据返回
        R redisData = getRedisData(SAFE, md5);
        if (redisData != null) {
            return redisData;
        }
        //缓存没有数据，直接从数据库中读取
        List<BrowserVisitSafe> visitMaps = browserVisitSafeMapper.selectList(
                new QueryWrapper<BrowserVisitSafe>().
                        select("FORBIDDEN_URL", "URL_DESCRIPTION").
                        eq("CHECK_STATUS", 0).eq("STATE", 0));
        List<BrowserPluginSafe> pluginMaps = browserPluginSafeMapper.selectList(
                new QueryWrapper<BrowserPluginSafe>().
                        select("PLUGIN_NAME", "PLUGIN_TYPE").
                        eq("CHECK_STATUS", 0).eq("STATE", 0));
        //如果数据库没有数据 直接返回不需要更新
        if (CollectionUtils.isEmpty(visitMaps) && CollectionUtils.isEmpty(pluginMaps)) {
            return R.result(CACHE_NOT_UPDATE);
        }
        Map<String, Object> dbMap = new HashMap<>(8);
        dbMap.put("plugins", pluginMaps);
        dbMap.put("visit", visitMaps);
        //计算新的MD5
        String newMd5 = ToolMD5.encodeMD5Hex(JSONObject.toJSONString(dbMap));
        //把查出来的数据放入缓存
        CacheUtils.put(SAFE, dbMap);
        //加入md5以便下次前端传值
        //由于使用的是内存缓存,缓存对象为同一个对象,缓存后map再次put,缓存也会变化
        //这里新建一个map对象把数据返还给调用方
        Map<String, Object> map = new HashMap<>();
        map.put("plugins", pluginMaps);
        map.put("visit", visitMaps);
        map.put("md5", newMd5);
        return R.success(map);
    }

    @Override
    public R configList(String md5) {
        //先从redis缓存中取数据如果有数据据返回
        R redisData = getRedisData(STRATEGY, md5);
        if (redisData != null) {
            return redisData;
        }
        //缓存没有数据，直接从数据库中读取
        List<BrowserCompatibleConfig> compatibleMaps = browserCompatibleConfigMapper.selectList(
                new QueryWrapper<BrowserCompatibleConfig>().select("URL", "KERNEL")
                        .eq("CHECK_STATUS", 0));
        BrowserGeneralConfig generalMaps = browserGeneralConfigMapper.selectOne(
                new QueryWrapper<BrowserGeneralConfig>().select("INDEX_PAGE", "COPY_STATE", "DRAG_STATE"
                        , "PRINT_STATE", "DOWNLOAD_STATE", "SCREENSHOT_STATE", "CACHE_STATE", "INTEGRITY_STATE")
                        .eq("CHECK_STATUS", 0));
        if (StringUtils.isEmpty(generalMaps.getIndexPage())) {
            generalMaps.setIndexPage("");
        }
        BrowserWatermarkConfig watermarkMaps = browserWatermarkConfigMapper.selectOne(
                new QueryWrapper<BrowserWatermarkConfig>().select("WATERMARK_STATE", "WATERMARK_NAME",
                        "DOWNLOAD_URL", "TRANSPARENCY")
                        .eq("CHECK_STATUS", 0));
        if (StringUtils.isEmpty(watermarkMaps.getDownloadUrl())) {
            watermarkMaps.setDownloadUrl("");
        }
        //如果数据库没有数据 直接返回为不需要更新
        if (CollectionUtils.isEmpty(compatibleMaps) && generalMaps == null
                && watermarkMaps == null) {
            return R.result(CACHE_NOT_UPDATE);
        }
        Map<String, Object> dbMap = new HashMap<>(8);
        dbMap.put("common", generalMaps);
        dbMap.put("compatibility", compatibleMaps);
        dbMap.put("watermark", watermarkMaps);
        //计算新的MD5
        String newMd5 = ToolMD5.encodeMD5Hex(JSONObject.toJSONString(dbMap));
        //把查出来的数据放入缓存
        CacheUtils.put(STRATEGY, dbMap);
        //加入md5以便下次前端传值
        Map<String, Object> map = new HashMap<>(8);
        map.put("common", generalMaps);
        map.put("compatibility", compatibleMaps);
        map.put("watermark", watermarkMaps);
        map.put("md5", newMd5);
        return R.success(map);
    }

    @Override
    public R infoCollect(InfoCollectRequest request) {
        BrowserLog browserLog = new BrowserLog();
        browserLog.setBrowserVersion(request.getBrowserVersion());
        browserLog.setIpAddress(request.getIpAddress());
        browserLog.setMac(request.getMac());
        browserLog.setTitle(request.getTitle());
        if (request.getTypeId() != null) {
            browserLog.setTypeId(request.getTypeId());
        }
        browserLog.setUrl(request.getUrl());
        browserLog.setCreateTime(new Date());
        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();
        browserLog.setDay(day);
        browserLog.setMonth(month);
        browserLog.setYear(year);
        browserLogMapper.insert(browserLog);
        return R.success();
    }

    /**
     * 从缓存中读取数据
     *
     * @param key
     * @param md5
     * @return null代表要数据库, 其他直接返回
     */
    private R getRedisData(String key, String md5) {
        if (StringUtils.isEmpty(md5) || "0".equals(md5)) {
            return null;
        }
        //不存在key直接返回null
        Object cache = CacheUtils.get(key);
        if (cache == null) {
            return null;
        }
        Map<String, Object> redisMap = (Map<String, Object>) cache;
        if (redisMap.size() == 0) {
            return null;
        }
        //比较md5是否一样，一样代表数据没有更新，返回特定状态码
        String s = ToolMD5.encodeMD5Hex(JSONObject.toJSONString(redisMap));
        if (md5.equals(s)) {
            return R.result(CACHE_NOT_UPDATE);
        }
        redisMap.put("md5", s);
        return R.success(redisMap);
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

    /**
     * 新浏览器行为统计接口
     * 与上面的行为统计接收参数不同
     * @param request
     */
    @Override
    public R actionLogCollect(ActionLogCollectRequest request) {
        BrowserActionLog browserActionLog = new BrowserActionLog();
        browserActionLog.setIpAddress(request.getIpAddress());
        browserActionLog.setMac(request.getMac());
        browserActionLog.setHardDiskSerial(request.getHardDiskSerial());
        browserActionLog.setOsVersionNum(request.getOsVersionNum());
        browserActionLog.setCreateTime(new Date());
        browserActionLogMapper.insert(browserActionLog);
        return R.success();
    }

    
}
