package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.dao.BrowserPackageUpgradeMapper;
import com.ht.common.entity.BrowserPackageUpgrade;
import com.ht.common.page.R;
import com.ht.common.request.upgrade.browser.BrowserUpgradeAddRequest;
import com.ht.common.request.upgrade.browser.BrowserUpgradePageRequest;
import com.ht.common.request.upgrade.browser.BrowserUpgradeUpdateRequest;
import com.ht.common.request.upgrade.browser.BrowserUpgradeVersionResetRequest;
import com.ht.common.service.IBrowserPackageUpgradeService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ht.common.constant.Constant.BROWSER_UPGRADE;
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
public class BrowserPackageUpgradeServiceImpl extends ServiceImpl<BrowserPackageUpgradeMapper, BrowserPackageUpgrade> implements IBrowserPackageUpgradeService {

    @Autowired
    private BrowserPackageUpgradeMapper browserPackageUpgradeMapper;
    @Autowired
    private UploadUtils uploadUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "上传浏览器安装包", type = OperateType.add)
    public R browserAdd(BrowserUpgradeAddRequest request) {
        //获取安装包对应的类型
        Integer type = HandleUtils.getType(request.getBrowserName());
        String version = HandleUtils.getVersion(request.getBrowserName());

        //查看数据库是否已经有安装包
        QueryWrapper<BrowserPackageUpgrade> wrapper = new QueryWrapper<>();
        wrapper.eq("BROWSER_TYPE", type).eq("HISTORY_STATUS", 0);
        BrowserPackageUpgrade one = browserPackageUpgradeMapper.selectOne(wrapper);
        //如果不为空把上个版本置为历史版本
        if (one != null) {
            one.setHistoryStatus(1);
            browserPackageUpgradeMapper.updateById(one);
        }
        //新包信息
        BrowserPackageUpgrade browserPackageUpgrade = new BrowserPackageUpgrade();
        browserPackageUpgrade.setId(UUIDUtil.getUUID());
        browserPackageUpgrade.setBrowserDescribe(request.getBrowserDescribe());
        browserPackageUpgrade.setBrowserName(request.getBrowserName());
        browserPackageUpgrade.setBrowserType(type);
        browserPackageUpgrade.setDownloadUrl(request.getDownloadUrl());
        browserPackageUpgrade.setVersion(version);
        browserPackageUpgrade.setConstraintStatus(request.getConstraintStatus());
        browserPackageUpgrade.setBrowserTypeName(HandleUtils.getTypeName(type));
        browserPackageUpgrade.setCreateTime(new Timestamp(System.currentTimeMillis()));
        browserPackageUpgrade.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserPackageUpgradeMapper.insert(browserPackageUpgrade);
        //删除缓存
        CacheUtils.remove(BROWSER_UPGRADE + type);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "更新浏览器安装包", type = OperateType.update)
    public R browserUpdate(BrowserUpgradeUpdateRequest request) {
        BrowserPackageUpgrade browserPackageUpgrade = new BrowserPackageUpgrade();
        browserPackageUpgrade.setId(request.getId());
        browserPackageUpgrade.setConstraintStatus(request.getConstraintStatus());
        browserPackageUpgrade.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserPackageUpgrade.setBrowserDescribe(request.getBrowserDescribe());
        browserPackageUpgradeMapper.updateById(browserPackageUpgrade);
        //删除缓存
        CacheUtils.remove(BROWSER_UPGRADE + request.getBrowserType());
        return R.success();
    }

    @Override
    public R browserList(BrowserUpgradePageRequest request) {
        Page<BrowserPackageUpgrade> employeePage = new Page<>();
        employeePage.setCurrent(request.getCurrent());
        employeePage.setSize(request.getOffset());
        QueryWrapper<BrowserPackageUpgrade> wrapper = new QueryWrapper<>();
        wrapper.eq("HISTORY_STATUS", 0);
        if (StringUtils.isNotEmpty(request.getBrowserName())) {
            wrapper.like("BROWSER_NAME", request.getBrowserName());
        }
        if (StringUtils.isNotEmpty(request.getVersion())) {
            wrapper.like("VERSION", request.getVersion());
        }
        if (request.getBrowserTypeName() != null) {
            wrapper.eq("BROWSER_TYPE_NAME", request.getBrowserTypeName());
        }
        if (request.getConstraintStatus() != null) {
            wrapper.eq("CONSTRAINT_STATUS", request.getConstraintStatus());
        }
        if (StringUtils.isNotEmpty(request.getCreateTime())) {
            wrapper.like("CREATE_TIME", request.getCreateTime());
        }
        wrapper.orderByDesc("CREATE_TIME");
        IPage<BrowserPackageUpgrade> employeeIPage = browserPackageUpgradeMapper.selectPage(employeePage, wrapper);
        return R.success(employeeIPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除浏览器安装包", type = OperateType.delete)
    public R browserDelete(String id) {
        Integer browserType = browserPackageUpgradeMapper.selectById(id).getBrowserType();
        browserPackageUpgradeMapper.deleteById(id);
        //删除缓存
        CacheUtils.remove(BROWSER_UPGRADE + browserType);
        return R.success();
    }

    @Override
    public R browserDetail(String id) {
        return R.success(browserPackageUpgradeMapper.selectById(id));
    }

    @Override
    public R browserUpload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!HandleUtils.handlePackage(fileName)) {
            return R.result(UN_SUPPORT_FORMAT);
        }
        String version = HandleUtils.getVersion(file.getOriginalFilename());
        Integer type = HandleUtils.getType(file.getOriginalFilename());
        //检验版本是否相同，如果相同不让添加
        if (browserPackageUpgradeMapper.selectOne(new QueryWrapper<BrowserPackageUpgrade>().
                eq("BROWSER_TYPE", type).
                eq("VERSION", version)) != null) {
            return R.result(VERSION_EXIST);
        }
        //文件上传，返回上传地址
        Map<String, String> upload = uploadUtils.upload(file, fileName, false);
        if (upload == null) {
            return R.result(UPLOAD_ERR);
        }
        Map<String, String> map = new HashMap<>(4);
        map.put("downloadUrl", upload.get("downloadUrl"));
        map.put("browserName", fileName);
        return R.success(map);
    }

    @Override
    public R browseHistory(Integer browserType) {
        QueryWrapper<BrowserPackageUpgrade> wrapper = new QueryWrapper<>();
        wrapper.eq("BROWSER_TYPE", browserType).eq("HISTORY_STATUS", 1).orderByDesc("CREATE_TIME");
        List<BrowserPackageUpgrade> browserPackageUpgrades = browserPackageUpgradeMapper.selectList(wrapper);
        return R.success(browserPackageUpgrades);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R browserReset(BrowserUpgradeVersionResetRequest request) {
        //找到同一类型的需要置为历史版本的数据
        BrowserPackageUpgrade history = browserPackageUpgradeMapper.selectOne(new QueryWrapper<BrowserPackageUpgrade>().
                eq("BROWSER_TYPE", request.getBrowserType()).
                eq("HISTORY_STATUS", 0));
        if (history != null) {
            history.setHistoryStatus(1);
            browserPackageUpgradeMapper.updateById(history);
        }

        //找到要重置的数据
        BrowserPackageUpgrade browserPackageUpgrade = browserPackageUpgradeMapper.selectById(request.getId());
        //设置为当前版本
        browserPackageUpgrade.setHistoryStatus(0);
        //更新
        browserPackageUpgradeMapper.updateById(browserPackageUpgrade);

        //清除redis缓存
        CacheUtils.remove(BROWSER_UPGRADE + request.getBrowserType());
        return R.success();
    }
}
