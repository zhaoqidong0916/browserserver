package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.dao.BrowserPluginSafeMapper;
import com.ht.common.dao.SystemOperateLogMapper;
import com.ht.common.entity.BrowserPluginSafe;
import com.ht.common.page.R;
import com.ht.common.request.safe.plugin.PluginAddRequest;
import com.ht.common.request.safe.plugin.PluginPageRequest;
import com.ht.common.request.safe.plugin.PluginStateEditRequest;
import com.ht.common.request.safe.plugin.PluginUpdateRequest;
import com.ht.common.service.IBrowserPluginSafeService;
import com.ht.common.utils.CacheUtils;
import com.ht.common.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.ht.common.constant.Constant.SAFE;
import static com.ht.common.constant.Constant.STRATEGY;
import static com.ht.common.enums.ResponseEnum.DATA_EXIST;

/**
 * 控件管控
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
@Service
public class BrowserPluginSafeServiceImpl extends ServiceImpl<BrowserPluginSafeMapper, BrowserPluginSafe> implements IBrowserPluginSafeService {

    @Autowired
    private BrowserPluginSafeMapper browserPluginSafeMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "添加控件管控", type = OperateType.add)
    public R pluginAdd(PluginAddRequest request) {
        if (browserPluginSafeMapper.selectCount(new QueryWrapper<BrowserPluginSafe>().
                eq("PLUGIN_TYPE", request.getPluginType())) > 0) {
            return R.result(DATA_EXIST);
        }
        BrowserPluginSafe browserPluginSafe = new BrowserPluginSafe();
        browserPluginSafe.setId(UUIDUtil.getUUID());
        browserPluginSafe.setPluginName(request.getPluginName());
        browserPluginSafe.setPluginType(request.getPluginType());
        browserPluginSafe.setCreateTime(new Timestamp(System.currentTimeMillis()));
        browserPluginSafe.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserPluginSafeMapper.insert(browserPluginSafe);
        //删除缓存
        CacheUtils.remove(SAFE);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "修改控件管控", type = OperateType.update)
    public R pluginUpdate(PluginUpdateRequest request) {
        BrowserPluginSafe browserPluginSafe = new BrowserPluginSafe();
        if (StringUtils.isNotEmpty(request.getPluginType())) {
            if (browserPluginSafeMapper.selectCount(new QueryWrapper<BrowserPluginSafe>().
                    eq("PLUGIN_TYPE", request.getPluginType())) > 0) {
                return R.result(DATA_EXIST);

            }
            browserPluginSafe.setPluginType(request.getPluginType());
        }
        browserPluginSafe.setId(request.getId());
        browserPluginSafe.setPluginName(request.getPluginName());
        browserPluginSafe.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserPluginSafeMapper.updateById(browserPluginSafe);
        //删除缓存
        CacheUtils.remove(SAFE);
        return R.success();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "修改控件黑白名单", type = OperateType.update)
    public R pluginStateEdit(PluginStateEditRequest request) {
        BrowserPluginSafe browserPluginSafe = new BrowserPluginSafe();
        browserPluginSafe.setId(request.getId());
        browserPluginSafe.setState(request.getState());
        browserPluginSafe.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserPluginSafeMapper.updateById(browserPluginSafe);
        //删除缓存
        CacheUtils.remove(SAFE);
        return R.success();
    }

    @Override
    public R pluginList(PluginPageRequest request) {
        Page<BrowserPluginSafe> browserVisitSafePage = new Page<>();
        browserVisitSafePage.setSize(request.getOffset());
        browserVisitSafePage.setCurrent(request.getCurrent());
        QueryWrapper<BrowserPluginSafe> browserVisitSafeQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getPluginName())) {
            browserVisitSafeQueryWrapper.like("PLUGIN_NAME", request.getPluginName());
        }
        if (StringUtils.isNotEmpty(request.getPluginType())) {
            browserVisitSafeQueryWrapper.like("PLUGIN_TYPE", request.getPluginType());
        }
        if (request.getState() != null) {
            browserVisitSafeQueryWrapper.eq("STATE", request.getState());
        }
        browserVisitSafeQueryWrapper.orderByDesc("CREATE_TIME");
        Page<BrowserPluginSafe> result = browserPluginSafeMapper.selectPage(browserVisitSafePage, browserVisitSafeQueryWrapper);
        return R.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除控件管控", type = OperateType.delete)
    public R pluginDelete(String id) {
        browserPluginSafeMapper.deleteById(id);
        //删除缓存
        CacheUtils.remove(SAFE);
        return R.success();
    }

    @Override
    public R pluginDetail(String id) {
        return R.success(browserPluginSafeMapper.selectById(id));
    }

}
