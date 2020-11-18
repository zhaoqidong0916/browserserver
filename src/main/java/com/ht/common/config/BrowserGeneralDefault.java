package com.ht.common.config;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.common.dao.BrowserGeneralConfigMapper;
import com.ht.common.dao.BrowserWatermarkConfigMapper;
import com.ht.common.entity.BrowserGeneralConfig;
import com.ht.common.entity.BrowserWatermarkConfig;
import com.ht.common.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;

/**
 * @description: 专门负责缓存的redis配置
 * @author: yaKun.shi
 * @create: 2020-03-06 09:20
 **/
@Configuration
public class BrowserGeneralDefault implements ApplicationRunner {

    @Autowired
    private BrowserGeneralConfigMapper browserGeneralConfigMapper;
    @Autowired
    private BrowserWatermarkConfigMapper browserWatermarkConfigMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Integer count = browserGeneralConfigMapper.selectCount(new QueryWrapper<>());
        if (count == 0) {
            BrowserGeneralConfig browserGeneralConfig = new BrowserGeneralConfig();
            // 非空字段，代码初始化值或数据库配置默认值
            browserGeneralConfig.setId(UUIDUtil.getUUID());
            browserGeneralConfig.setCopyState(1);
            browserGeneralConfig.setDragState(1);
            browserGeneralConfig.setPrintState(1);
            browserGeneralConfig.setDownloadState(1);
            browserGeneralConfig.setScreenshotState(1);
            browserGeneralConfig.setCacheState(1);
            browserGeneralConfig.setIntegrityState(1);
            browserGeneralConfig.setPrivateState(1);
            browserGeneralConfig.setReviewStatus(0);
            browserGeneralConfig.setCheckStatus(0);
            browserGeneralConfig.setCreateTime(new Timestamp(System.currentTimeMillis()));
            browserGeneralConfig.setCertState(1);
            browserGeneralConfig.setPluginState(1);
            browserGeneralConfig.setWatermarkState(1);
            browserGeneralConfigMapper.insert(browserGeneralConfig);
        }
        Integer count1 = browserWatermarkConfigMapper.selectCount(new QueryWrapper<>());
        if (count1 == 0) {
            BrowserWatermarkConfig browserWatermarkConfig = new BrowserWatermarkConfig();
            browserWatermarkConfig.setCreateTime(new Timestamp(System.currentTimeMillis()));
            browserWatermarkConfig.setTransparency(50);
            browserWatermarkConfig.setWatermarkName("海泰方圆");
            browserWatermarkConfig.setId(UUIDUtil.getUUID());
            browserWatermarkConfigMapper.insert(browserWatermarkConfig);
        }

    }
}
