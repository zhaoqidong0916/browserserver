package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.dao.BrowserPluginTypeMapper;
import com.ht.common.dao.BrowserPluginUpgradeMapper;
import com.ht.common.entity.BrowserPluginUpgrade;
import com.ht.common.page.R;
import com.ht.common.request.upgrade.plugins.BrowserPluginUpgradeAddRequest;
import com.ht.common.request.upgrade.plugins.BrowserPluginUpgradePageRequest;
import com.ht.common.request.upgrade.plugins.BrowserPluginUpgradeUpdateRequest;
import com.ht.common.service.IBrowserPluginUpgradeService;
import com.ht.common.utils.CacheUtils;
import com.ht.common.utils.HandleUtils;
import com.ht.common.utils.UUIDUtil;
import com.ht.common.utils.upload.UploadUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.ht.common.constant.Constant.PLUGIN_UPGRADE;
import static com.ht.common.enums.ResponseEnum.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-11
 */
@Service
public class BrowserPluginUpgradeServiceImpl extends ServiceImpl<BrowserPluginUpgradeMapper, BrowserPluginUpgrade> implements IBrowserPluginUpgradeService {

    @Autowired
    private BrowserPluginUpgradeMapper pluginSafeMapper;

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private BrowserPluginTypeMapper browserPluginTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "上传控件安装包", type = OperateType.add)
    public R pluginAdd(BrowserPluginUpgradeAddRequest request) {
        Integer pluginOsType = HandleUtils.getPluginOsType(request.getPluginName());
        Integer count = pluginSafeMapper.selectCount(new QueryWrapper<BrowserPluginUpgrade>().
                eq("PLUGIN_TYPE", request.getPluginType()).
                eq("CHECK_STATUS", 0).
                eq("TYPE", pluginOsType));
        if (count > 0) {
            return R.result(IS_UPDATE);
        }
        BrowserPluginUpgrade browserPluginUpgrade = new BrowserPluginUpgrade();
        browserPluginUpgrade.setPluginType(request.getPluginType());
        browserPluginUpgrade.setPluginTypeName(request.getPluginTypeName());
        browserPluginUpgrade.setPluginName(request.getPluginName());
        browserPluginUpgrade.setDownloadUrl(request.getDownloadUrl());
        browserPluginUpgrade.setPluginDescribe(request.getPluginDescribe());
        browserPluginUpgrade.setMd5(request.getMd5());
        browserPluginUpgrade.setConstraintStatus(1);
        //现阶段只有一个1

        browserPluginUpgrade.setType(pluginOsType);
        browserPluginUpgrade.setCreateTime(new Timestamp(System.currentTimeMillis()));
        browserPluginUpgrade.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        //如果数据库没有相同控件类型的数据，直接插入数据
        browserPluginUpgrade.setId(UUIDUtil.getUUID());
        pluginSafeMapper.insert(browserPluginUpgrade);

        //删除缓存
        CacheUtils.remove(PLUGIN_UPGRADE + pluginOsType);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R pluginAddUpdate(BrowserPluginUpgradeAddRequest request) {
        BrowserPluginUpgrade browserPluginUpgrade = new BrowserPluginUpgrade();
        browserPluginUpgrade.setPluginType(request.getPluginType());
        browserPluginUpgrade.setPluginTypeName(request.getPluginTypeName());
        browserPluginUpgrade.setPluginName(request.getPluginName());
        browserPluginUpgrade.setDownloadUrl(request.getDownloadUrl());
        browserPluginUpgrade.setPluginDescribe(request.getPluginDescribe());
        browserPluginUpgrade.setMd5(request.getMd5());
        browserPluginUpgrade.setConstraintStatus(1);
        //获取控件适配的操作系统
        Integer osType = HandleUtils.getPluginOsType(request.getPluginName());
        browserPluginUpgrade.setType(osType);
        browserPluginUpgrade.setCreateTime(new Timestamp(System.currentTimeMillis()));
        browserPluginUpgrade.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        BrowserPluginUpgrade pluginUpgrade = pluginSafeMapper.selectOne(new QueryWrapper<BrowserPluginUpgrade>().
                eq("PLUGIN_TYPE", request.getPluginType()).
                eq("CHECK_STATUS", 0).
                eq("TYPE", osType));
        if (pluginUpgrade != null) {
            browserPluginUpgrade.setId(pluginUpgrade.getId());
            pluginSafeMapper.updateById(browserPluginUpgrade);

        } else {
            browserPluginUpgrade.setId(UUIDUtil.getUUID());
            pluginSafeMapper.insert(browserPluginUpgrade);
        }
        //删除缓存
        CacheUtils.remove(PLUGIN_UPGRADE + osType);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "更新控件安装包", type = OperateType.update)
    public R pluginUpdate(BrowserPluginUpgradeUpdateRequest request) {
        BrowserPluginUpgrade browserPluginUpgrade = new BrowserPluginUpgrade();
        browserPluginUpgrade.setPluginDescribe(request.getPluginDescribe());
        browserPluginUpgrade.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserPluginUpgrade.setId(request.getId());
        pluginSafeMapper.updateById(browserPluginUpgrade);

        //删除缓存
        CacheUtils.remove(PLUGIN_UPGRADE + request.getType());
        return R.success();
    }

    @Override
    public R pluginList(BrowserPluginUpgradePageRequest request) {
        Page<BrowserPluginUpgrade> employeePage = new Page<>();
        employeePage.setCurrent(request.getCurrent());
        employeePage.setSize(request.getOffset());
        QueryWrapper<BrowserPluginUpgrade> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getPluginName())) {
            wrapper.like("PLUGIN_NAME", request.getPluginName());
        }
        if (StringUtils.isNotEmpty(request.getPluginTypeName())) {
            wrapper.eq("PLUGIN_TYPE_NAME", request.getPluginTypeName());
        }
        if (StringUtils.isNotEmpty(request.getCreateTime())) {
            wrapper.like("CREATE_TIME", request.getCreateTime());
        }
        wrapper.orderByDesc("CREATE_TIME");
        IPage<BrowserPluginUpgrade> employeeIPage = pluginSafeMapper.selectPage(employeePage, wrapper);
        return R.success(employeeIPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除控件安装包", type = OperateType.delete)
    public R pluginDelete(String id) {
        BrowserPluginUpgrade pluginUpgrade = pluginSafeMapper.selectById(id);
        Integer osType = pluginUpgrade.getType();
        pluginSafeMapper.deleteById(id);
        //删除缓存
        CacheUtils.remove(PLUGIN_UPGRADE + osType);
        return R.success();
    }

    @Override
    public R pluginDetail(String id) {
        return R.success(pluginSafeMapper.selectById(id));
    }

    @Override
    public R pluginUpload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (HandleUtils.getPluginOsType(fileName) == -1) {
            return R.result(UN_SUPPORT_FORMAT);
        }
        //文件上传，返回上传地址
        Map<String, String> upload = uploadUtils.upload(file, fileName, true);
        if (upload == null) {
            return R.result(UPLOAD_ERR);
        }
        Map<String, String> map = new HashMap<>(4);
        map.put("pluginName", fileName);
        map.put("downloadUrl", upload.get("downloadUrl"));
        map.put("md5", upload.get("md5"));
        return R.success(map);
    }

    @Override
    public R pluginTypeListForUpgrade() {
        return R.success(browserPluginTypeMapper.selectList(new QueryWrapper<>()));
    }

}
