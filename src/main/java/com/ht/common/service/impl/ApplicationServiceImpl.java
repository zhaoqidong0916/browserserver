package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.common.dao.BrowserApplicationResourcesMapper;
import com.ht.common.dao.BrowserApplicationUrlMapper;
import com.ht.common.entity.BrowserApplicationResources;
import com.ht.common.entity.BrowserApplicationUrl;
import com.ht.common.page.R;
import com.ht.common.service.IApplicationService;
import com.ht.common.utils.CacheUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import static com.ht.common.constant.Constant.APPLICATION;
import static com.ht.common.constant.Constant.APPLICATION_URL;
import static com.ht.common.enums.ResponseEnum.CACHE_NOT_UPDATE;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-11 15:27
 **/
@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private BrowserApplicationUrlMapper urlMapper;

    @Autowired
    private BrowserApplicationResourcesMapper resourcesMapper;

    @Override
    public R resourceList() {
        R redisData = getCache(APPLICATION);
        if (redisData != null) {
            return redisData;
        }
        QueryWrapper<BrowserApplicationResources> wrapper = new QueryWrapper<>();
        wrapper.select("DOWNLOAD_URL");
        wrapper.eq("CHECK_STATUS", 0);
        wrapper.between("RESOURCES_TYPE", 2, 3);
        List<BrowserApplicationResources> browserApplicationResources = resourcesMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(browserApplicationResources)) {
            return R.result(CACHE_NOT_UPDATE);
        }
        CacheUtils.put(APPLICATION, browserApplicationResources);
        return R.success(browserApplicationResources);
    }

    @Override
    public R urlList() {
        R redisData = getCache(APPLICATION_URL);
        if (redisData != null) {
            return redisData;
        }
        QueryWrapper<BrowserApplicationUrl> wrapper = new QueryWrapper<>();
        wrapper.select("URL_NAME","ICON_URL","URL_ADDRESS");
        wrapper.eq("CHECK_STATUS", 0);
        List<BrowserApplicationUrl> browserApplicationUrls = urlMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(browserApplicationUrls)) {
            return R.result(CACHE_NOT_UPDATE);
        }
        CacheUtils.put(APPLICATION_URL, browserApplicationUrls);
        return R.success(browserApplicationUrls);
    }


    /**
     * 从缓存中读取数据
     *
     * @return null代表要数据库, 其他直接返回
     */
    private R getCache(String key) {
        //不存在key直接返回null
        Object cache = CacheUtils.get(key);
        if (cache == null) {
            return null;
        }
        return R.success(cache);
    }
    public boolean headList(HttpServletRequest ret){
    
      Enumeration<String> headerNames = ret.getHeaderNames();
      while (headerNames.hasMoreElements())
      {
        String name = (String)headerNames.nextElement();
        
        String value = ret.getHeader(name);
        if ((name.equals("token")) && (value.equals("103702738237"))) {
          return true;
        }
      }
      return false;
    }
}
