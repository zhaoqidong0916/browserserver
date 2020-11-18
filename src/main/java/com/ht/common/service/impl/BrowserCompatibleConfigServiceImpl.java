package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.dao.BrowserCompatibleConfigMapper;
import com.ht.common.entity.BrowserCompatibleConfig;
import com.ht.common.page.R;
import com.ht.common.request.compatible.CompatibleAddRequest;
import com.ht.common.request.compatible.CompatiblePageRequest;
import com.ht.common.request.compatible.CompatibleUpdateRequest;
import com.ht.common.service.IBrowserCompatibleConfigService;
import com.ht.common.utils.CacheUtils;
import com.ht.common.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.ht.common.constant.Constant.PLUGIN_UPGRADE;
import static com.ht.common.constant.Constant.STRATEGY;
import static com.ht.common.enums.ResponseEnum.DATA_EXIST;

/**
 * 浏览器兼容配置
 *
 * @author yakun.shi
 * @since 2020-03-06
 */
@Service
public class BrowserCompatibleConfigServiceImpl extends ServiceImpl<BrowserCompatibleConfigMapper, BrowserCompatibleConfig> implements IBrowserCompatibleConfigService {

    @Autowired
    private BrowserCompatibleConfigMapper browserCompatibleConfigMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "添加浏览器兼容配置", type = OperateType.add)
    public R compatibleAdd(CompatibleAddRequest request) {
        QueryWrapper<BrowserCompatibleConfig> wrapper = new QueryWrapper<BrowserCompatibleConfig>().eq("URL", request.getUrl());
        if (browserCompatibleConfigMapper.selectCount(wrapper) > 0) {
            return R.result(DATA_EXIST);
        }
        BrowserCompatibleConfig browserCompatibleConfig = new BrowserCompatibleConfig();
        browserCompatibleConfig.setId(UUIDUtil.getUUID());
        browserCompatibleConfig.setKernel(request.getKernel());
        browserCompatibleConfig.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserCompatibleConfig.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserCompatibleConfig.setUrl(request.getUrl());
        browserCompatibleConfigMapper.insert(browserCompatibleConfig);

        //删除缓存
        CacheUtils.remove(STRATEGY);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "修改浏览器兼容配置", type = OperateType.update)
    public R compatibleUpdate(CompatibleUpdateRequest request) {
        BrowserCompatibleConfig browserCompatibleConfig = new BrowserCompatibleConfig();
        if (StringUtils.isNotEmpty(request.getUrl())) {
            QueryWrapper<BrowserCompatibleConfig> wrapper = new QueryWrapper<BrowserCompatibleConfig>().eq("URL", request.getUrl());
            if (browserCompatibleConfigMapper.selectCount(wrapper) > 0) {
                return R.result(DATA_EXIST);
            }
            browserCompatibleConfig.setUrl(request.getUrl());
        }
        browserCompatibleConfig.setId(request.getId());
        browserCompatibleConfig.setKernel(request.getKernel());
        browserCompatibleConfig.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserCompatibleConfigMapper.updateById(browserCompatibleConfig);
        //删除缓存
        CacheUtils.remove(STRATEGY);
        return R.success();
    }

    @Override
    public R compatibleList(CompatiblePageRequest request) {
        Page<BrowserCompatibleConfig> employeePage = new Page<>();
        employeePage.setCurrent(request.getCurrent());
        employeePage.setSize(request.getOffset());
        QueryWrapper<BrowserCompatibleConfig> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getKernel())) {
            wrapper.like("KERNEL", request.getKernel());
        }
        if (StringUtils.isNotEmpty(request.getUrl())) {
            wrapper.like("URL", request.getUrl());
        }
        wrapper.orderByDesc("CREATE_TIME");
        IPage<BrowserCompatibleConfig> employeeIPage = browserCompatibleConfigMapper.selectPage(employeePage, wrapper);
        return R.success(employeeIPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除浏览器兼容配置", type = OperateType.delete)
    public R compatibleDelete(String id) {
        browserCompatibleConfigMapper.deleteById(id);
        //删除缓存
        CacheUtils.remove(STRATEGY);
        return R.success();
    }

    @Override
    public R compatibleDetail(String id) {
        BrowserCompatibleConfig browserCompatibleConfig = browserCompatibleConfigMapper.selectById(id);
        return R.success(browserCompatibleConfig);
    }
}
