package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.dao.BrowserCertTypeMapper;
import com.ht.common.dao.BrowserCertUpgradeMapper;
import com.ht.common.entity.BrowserCertUpgrade;
import com.ht.common.page.R;
import com.ht.common.request.upgrade.cert.upgrade.CertUpgradeAddRequest;
import com.ht.common.request.upgrade.cert.upgrade.CertUpgradePageRequest;
import com.ht.common.request.upgrade.cert.upgrade.CertUpgradeUpdateRequest;
import com.ht.common.service.IBrowserCertUpgradeService;
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

import static com.ht.common.constant.Constant.CERT_UPGRADE;
import static com.ht.common.enums.ResponseEnum.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-23
 */
@Service
public class BrowserCertUpgradeServiceImpl extends ServiceImpl<BrowserCertUpgradeMapper, BrowserCertUpgrade> implements IBrowserCertUpgradeService {

    @Autowired
    private BrowserCertUpgradeMapper browserCertUpgradeMapper;

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private BrowserCertTypeMapper browserCertTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "添加证书", type = OperateType.add)
    public R certAdd(CertUpgradeAddRequest request) {
        Integer count = browserCertUpgradeMapper.selectCount(new QueryWrapper<BrowserCertUpgrade>().
                eq("CERT_TYPE", request.getCertType()).eq("CHECK_STATUS", 0));
        if (count > 0) {
            return R.result(IS_UPDATE);
        }
        //如果数据库没有相同证书类型的数据，直接添加
        BrowserCertUpgrade browserCertUpgrade = new BrowserCertUpgrade();
        browserCertUpgrade.setCertName(request.getCertName());
        browserCertUpgrade.setCertType(request.getCertType());
        browserCertUpgrade.setCertTypeName(request.getCertTypeName());
        browserCertUpgrade.setCertDescribe(request.getCertDescribe());
        browserCertUpgrade.setDownloadUrl(request.getDownloadUrl());
        browserCertUpgrade.setMd5(request.getMd5());
        browserCertUpgrade.setCreateTime(new Timestamp(System.currentTimeMillis()));
        browserCertUpgrade.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        //默认全部强制更新
        browserCertUpgrade.setConstraintStatus(1);
        //现阶段只有一个1  代表win
        browserCertUpgrade.setType(1);
        browserCertUpgrade.setId(UUIDUtil.getUUID());
        browserCertUpgradeMapper.insert(browserCertUpgrade);

        //删除缓存
        CacheUtils.remove(CERT_UPGRADE + 1);
        return R.success();
    }


    @Override
    public R certAddUpdate(CertUpgradeAddRequest request) {
        BrowserCertUpgrade browserCertUpgrade = new BrowserCertUpgrade();
        browserCertUpgrade.setCertName(request.getCertName());
        browserCertUpgrade.setCertType(request.getCertType());
        browserCertUpgrade.setCertTypeName(request.getCertTypeName());
        browserCertUpgrade.setCertDescribe(request.getCertDescribe());
        browserCertUpgrade.setDownloadUrl(request.getDownloadUrl());
        browserCertUpgrade.setMd5(request.getMd5());
        browserCertUpgrade.setCreateTime(new Timestamp(System.currentTimeMillis()));
        browserCertUpgrade.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        //默认全部强制更新
        browserCertUpgrade.setConstraintStatus(1);
        //现阶段只有一个1  代表win
        browserCertUpgrade.setType(1);
        BrowserCertUpgrade certUpgrade = browserCertUpgradeMapper.selectOne(new QueryWrapper<BrowserCertUpgrade>().
                eq("CERT_TYPE", request.getCertType()).eq("CHECK_STATUS", 0));
        if (certUpgrade != null) {
            browserCertUpgrade.setId(certUpgrade.getId());
            browserCertUpgradeMapper.updateById(browserCertUpgrade);
        } else {
            browserCertUpgrade.setId(UUIDUtil.getUUID());
            browserCertUpgradeMapper.insert(browserCertUpgrade);
        }
        //删除缓存
        CacheUtils.remove(CERT_UPGRADE + 1);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "更新证书", type = OperateType.update)
    public R certUpdate(CertUpgradeUpdateRequest request) {
        BrowserCertUpgrade browserCertUpgrade = new BrowserCertUpgrade();
        browserCertUpgrade.setId(request.getId());
        browserCertUpgrade.setCertDescribe(request.getCertDescribe());
        browserCertUpgrade.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserCertUpgradeMapper.updateById(browserCertUpgrade);

        //删除缓存
        CacheUtils.remove(CERT_UPGRADE + 1);
        return R.success();
    }

    @Override
    public R certList(CertUpgradePageRequest request) {
        Page<BrowserCertUpgrade> employeePage = new Page<>();
        employeePage.setCurrent(request.getCurrent());
        employeePage.setSize(request.getOffset());
        QueryWrapper<BrowserCertUpgrade> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getCertName())) {
            wrapper.like("CERT_NAME", request.getCertName());
        }
        if (StringUtils.isNotEmpty(request.getCertTypeName())) {
            wrapper.eq("CERT_TYPE_NAME", request.getCertTypeName());
        }
        if (StringUtils.isNotEmpty(request.getCreateTime())) {
            wrapper.like("CREATE_TIME", request.getCreateTime());
        }
        wrapper.orderByDesc("CREATE_TIME");
        IPage<BrowserCertUpgrade> employeeIPage = browserCertUpgradeMapper.selectPage(employeePage, wrapper);
        return R.success(employeeIPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除证书", type = OperateType.delete)
    public R certDelete(String id) {
        browserCertUpgradeMapper.deleteById(id);
        //删除缓存
        CacheUtils.remove(CERT_UPGRADE + 1);
        return R.success();
    }

    @Override
    public R certDetail(String id) {

        return R.success(browserCertUpgradeMapper.selectById(id));
    }

    @Override
    public R certUpload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!HandleUtils.isCer(fileName)) {
            return R.result(UN_SUPPORT_FORMAT);
        }
        //文件上传，返回上传地址
        Map<String, String> upload = uploadUtils.upload(file, fileName, true);
        if (upload == null) {
            return R.result(UPLOAD_ERR);
        }
        Map<String, String> map = new HashMap<>(4);
        map.put("certName", fileName);
        map.put("downloadUrl", upload.get("downloadUrl"));
        map.put("md5", upload.get("md5"));
        return R.success(map);
    }

    @Override
    public R certTypeListForUpgrade() {
        return R.success(browserCertTypeMapper.selectList(new QueryWrapper<>()));
    }


}
