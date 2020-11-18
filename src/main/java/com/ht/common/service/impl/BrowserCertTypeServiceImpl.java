package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.dao.BrowserCertTypeMapper;
import com.ht.common.dao.BrowserCertUpgradeMapper;
import com.ht.common.entity.BrowserCertType;
import com.ht.common.entity.BrowserCertUpgrade;
import com.ht.common.page.R;
import com.ht.common.request.upgrade.cert.type.CertTypeAddRequest;
import com.ht.common.request.upgrade.cert.type.CertTypePageReqeust;
import com.ht.common.request.upgrade.cert.type.CertTypeUpdateRequest;
import com.ht.common.service.IBrowserCertTypeService;
import com.ht.common.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.ht.common.enums.ResponseEnum.CERT_TYPE_USED;
import static com.ht.common.enums.ResponseEnum.DATA_EXIST;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-23
 */
@Service
public class BrowserCertTypeServiceImpl extends ServiceImpl<BrowserCertTypeMapper, BrowserCertType> implements IBrowserCertTypeService {


    @Autowired
    private BrowserCertTypeMapper browserCertTypeMapper;


    @Autowired
    private BrowserCertUpgradeMapper browserCertUpgradeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "添加证书类型",type = OperateType.add)
    public R certTypeAdd(CertTypeAddRequest request) {
        Integer count = browserCertTypeMapper.selectCount(new QueryWrapper<BrowserCertType>().eq("CERT_TYPE_NAME", request.getCertTypeName()));
        if (count > 0) {
            return R.result(DATA_EXIST);
        }
        BrowserCertType browserCertType = new BrowserCertType();
        browserCertType.setCertType(UUIDUtil.getUUID());
        browserCertType.setCertTypeName(request.getCertTypeName());
        browserCertType.setId(UUIDUtil.getUUID());
        browserCertType.setCreateTime(new Timestamp(System.currentTimeMillis()));
        browserCertType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserCertTypeMapper.insert(browserCertType);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "更新证书类型",type = OperateType.update)
    public R certTypeUpdate(CertTypeUpdateRequest request) {
        Integer count = browserCertUpgradeMapper.selectCount(new QueryWrapper<BrowserCertUpgrade>().eq("CERT_TYPE_NAME", request.getCertTypeName()));
        if (count>0) {
            return R.result(CERT_TYPE_USED);
        }
        BrowserCertType browserCertType = new BrowserCertType();
        browserCertType.setCertType(UUIDUtil.getUUID());
        browserCertType.setCertTypeName(request.getCertTypeName());
        browserCertType.setId(request.getId());
        browserCertType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserCertTypeMapper.updateById(browserCertType);
        return R.success();
    }

    @Override
    public R certTypeList(CertTypePageReqeust request) {
        Page<BrowserCertType> browserVisitSafePage = new Page<>();
        browserVisitSafePage.setSize(request.getOffset());
        browserVisitSafePage.setCurrent(request.getCurrent());
        QueryWrapper<BrowserCertType> browserVisitSafeQueryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(request.getCertTypeName())){
            browserVisitSafeQueryWrapper.like("CERT_TYPE_NAME",request.getCertTypeName());
        }
        browserVisitSafeQueryWrapper.orderByDesc("CREATE_TIME");
        Page<BrowserCertType> result = browserCertTypeMapper.selectPage(browserVisitSafePage, browserVisitSafeQueryWrapper);
        return R.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除证书类型",type = OperateType.delete)
    public R certTypeDelete(String id) {
        BrowserCertType browserCertType = browserCertTypeMapper.selectById(id);
        String certType = browserCertType.getCertType();
        Integer count = browserCertUpgradeMapper.selectCount(new QueryWrapper<BrowserCertUpgrade>().eq("CERT_TYPE",certType));
        if (count>0) {
            return R.result(CERT_TYPE_USED);
        }
        browserCertTypeMapper.deleteById(id);
        return R.success();
    }

    @Override
    public R certTypeDetail(String id) {
        return R.success(browserCertTypeMapper.selectById(id));
    }
}
