package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.dao.BrowserGeneralConfigMapper;
import com.ht.common.entity.BrowserGeneralConfig;
import com.ht.common.page.R;
import com.ht.common.request.general.GeneralUpdateRequest;
import com.ht.common.service.IBrowserGeneralConfigService;
import com.ht.common.utils.CacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.ht.common.constant.Constant.STRATEGY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-06
 */
@Service
public class BrowserGeneralConfigServiceImpl extends ServiceImpl<BrowserGeneralConfigMapper, BrowserGeneralConfig> implements IBrowserGeneralConfigService {

    @Autowired
    private BrowserGeneralConfigMapper browserGeneralConfigMapper;

    @Override
    public R generalUpdate(GeneralUpdateRequest request) {
        BrowserGeneralConfig browserGeneralConfig = new BrowserGeneralConfig();
        browserGeneralConfig.setIndexPage(request.getIndexPage());
        browserGeneralConfig.setCacheState(request.getCacheState());
        browserGeneralConfig.setCopyState(request.getCopyState());
        browserGeneralConfig.setDownloadState(request.getDownloadState());
        browserGeneralConfig.setDragState(request.getDragState());
        browserGeneralConfig.setId(request.getId());
        browserGeneralConfig.setIntegrityState(request.getIntegrityState());
        browserGeneralConfig.setScreenshotState(request.getScreenshotState());
        browserGeneralConfig.setPrintState(request.getPrintState());
        browserGeneralConfig.setCreateTime(new Timestamp(System.currentTimeMillis()));
        browserGeneralConfig.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserGeneralConfigMapper.updateById(browserGeneralConfig);
        //删除缓存
        CacheUtils.remove(STRATEGY);
        return R.success();
    }

    @Override
    public R generalList() {
        List<BrowserGeneralConfig> browserGeneralConfigs = browserGeneralConfigMapper.selectList(new QueryWrapper<>());
        return R.success(browserGeneralConfigs);
    }

    @Override
    public R GeneralDetail(String id) {
        BrowserGeneralConfig browserGeneralConfig = browserGeneralConfigMapper.selectById(id);
        return R.success(browserGeneralConfig);
    }
}
