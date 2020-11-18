package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.service.IBrowserPluginTypeService;
import com.ht.common.dao.BrowserPluginTypeMapper;
import com.ht.common.dao.BrowserPluginUpgradeMapper;
import com.ht.common.entity.BrowserPluginType;
import com.ht.common.entity.BrowserPluginUpgrade;
import com.ht.common.page.R;
import com.ht.common.request.upgrade.plugintype.PluginTypeAddRequest;
import com.ht.common.request.upgrade.plugintype.PluginTypePageRequest;
import com.ht.common.request.upgrade.plugintype.PluginTypeUpdateRequest;
import com.ht.common.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.ht.common.enums.ResponseEnum.DATA_EXIST;
import static com.ht.common.enums.ResponseEnum.PLUGIN_TYPE_USED;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-16
 */
@Service
public class BrowserPluginTypeServiceImpl extends ServiceImpl<BrowserPluginTypeMapper, BrowserPluginType> implements IBrowserPluginTypeService {

    @Autowired
    private BrowserPluginTypeMapper browserPluginTypeMapper;
    @Autowired
    private BrowserPluginUpgradeMapper browserPluginUpgradeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "添加控件类型",type = OperateType.add)
    public R pluginTypeAdd(PluginTypeAddRequest request) {
        Integer count = browserPluginTypeMapper.selectCount(new QueryWrapper<BrowserPluginType>().eq("PLUGIN_TYPE_NAME", request.getPluginTypeName()));
        if(count>0){
            return R.result(DATA_EXIST);
        }
        BrowserPluginType browserPluginType = new BrowserPluginType();
        browserPluginType.setId(UUIDUtil.getUUID());
        browserPluginType.setPluginType(UUIDUtil.getUUID());
        browserPluginType.setPluginTypeName(request.getPluginTypeName());
        browserPluginType.setCreateTime(new Timestamp(System.currentTimeMillis()));
        browserPluginType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserPluginTypeMapper.insert(browserPluginType);
        return R.success();
    }

    @Override
    @OperateLogger(value = "修改控件类型",type = OperateType.update)
    public R pluginTypeUpdate(PluginTypeUpdateRequest request) {
        Integer count = browserPluginUpgradeMapper.selectCount(new QueryWrapper<BrowserPluginUpgrade>().eq("PLUGIN_TYPE", request.getPluginType()));
        if (count > 0) {
            return R.result(PLUGIN_TYPE_USED);
        }
        BrowserPluginType browserPluginType = new BrowserPluginType();
        browserPluginType.setId(request.getId());
        browserPluginType.setPluginType(UUIDUtil.getUUID());
        browserPluginType.setPluginTypeName(request.getPluginTypeName());
        browserPluginType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserPluginTypeMapper.updateById(browserPluginType);
        return R.success();
    }

    @Override
    public R pluginTypeList(PluginTypePageRequest request) {
        Page<BrowserPluginType> browserVisitSafePage = new Page<>();
        browserVisitSafePage.setSize(request.getOffset());
        browserVisitSafePage.setCurrent(request.getCurrent());
        QueryWrapper<BrowserPluginType> browserVisitSafeQueryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(request.getPluginTypeName())){
            browserVisitSafeQueryWrapper.like("PLUGIN_TYPE_NAME",request.getPluginTypeName());
        }
        browserVisitSafeQueryWrapper.orderByDesc("CREATE_TIME");
        Page<BrowserPluginType> result = browserPluginTypeMapper.selectPage(browserVisitSafePage, browserVisitSafeQueryWrapper);
        return R.success(result);
    }

    @Override
    @OperateLogger(value = "删除控件类型",type = OperateType.delete)
    public R pluginTypeDelete(String id) {
        //查看控件安装包是否有此类型
        String pluginType = browserPluginTypeMapper.selectById(id).getPluginType();
        Integer count = browserPluginUpgradeMapper.selectCount(new QueryWrapper<BrowserPluginUpgrade>().eq("PLUGIN_TYPE", pluginType));
        if (count > 0) {
            return R.result(PLUGIN_TYPE_USED);
        }
        browserPluginTypeMapper.deleteById(id);
        return R.success();
    }

    @Override
    public R pluginTypeDetail(String id) {
        return R.success(browserPluginTypeMapper.selectById(id));
    }
}
