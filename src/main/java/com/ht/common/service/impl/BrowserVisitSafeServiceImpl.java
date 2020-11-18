package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.dao.BrowserVisitSafeMapper;
import com.ht.common.entity.BrowserVisitSafe;
import com.ht.common.page.R;
import com.ht.common.request.safe.visit.VisitAddRequest;
import com.ht.common.request.safe.visit.VisitPageRequest;
import com.ht.common.request.safe.visit.VisitStateEditRequest;
import com.ht.common.request.safe.visit.VisitUpdateRequest;
import com.ht.common.service.IBrowserVisitSafeService;
import com.ht.common.utils.CacheUtils;
import com.ht.common.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.ht.common.constant.Constant.SAFE;
import static com.ht.common.enums.ResponseEnum.DATA_EXIST;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
@Service
public class BrowserVisitSafeServiceImpl extends ServiceImpl<BrowserVisitSafeMapper, BrowserVisitSafe> implements IBrowserVisitSafeService {

    @Autowired
    private BrowserVisitSafeMapper browserVisitSafeMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "添加访问黑名单",type = OperateType.add)
    public R visitAdd(VisitAddRequest request) {
        if(browserVisitSafeMapper.selectCount(new QueryWrapper<BrowserVisitSafe>().
                eq("FORBIDDEN_URL",request.getForbiddenUrl()))>0){
            return R.result(DATA_EXIST);
        }
        BrowserVisitSafe browserVisitSafe = new BrowserVisitSafe();
        browserVisitSafe.setForbiddenUrl(request.getForbiddenUrl());
        browserVisitSafe.setUrlDescription(request.getUrlDescription());
        browserVisitSafe.setId(UUIDUtil.getUUID());
        browserVisitSafe.setCreateTime(new Timestamp(System.currentTimeMillis()));
        browserVisitSafe.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserVisitSafeMapper.insert(browserVisitSafe);
        //删除缓存
        CacheUtils.remove(SAFE);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "修改访问黑名单",type = OperateType.update)
    public R visitUpdate(VisitUpdateRequest request) {
        BrowserVisitSafe browserVisitSafe = new BrowserVisitSafe();
        if(StringUtils.isNotEmpty(request.getForbiddenUrl())){
            if(browserVisitSafeMapper.selectCount(new QueryWrapper<BrowserVisitSafe>().
                    eq("FORBIDDEN_URL",request.getForbiddenUrl()))>0){
                return R.result(DATA_EXIST);
            }
            browserVisitSafe.setForbiddenUrl(request.getForbiddenUrl());
        }
        browserVisitSafe.setUrlDescription(request.getUrlDescription());
        browserVisitSafe.setId(request.getId());
        browserVisitSafe.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserVisitSafeMapper.updateById(browserVisitSafe);
        //删除缓存
        CacheUtils.remove(SAFE);
        return R.success();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "编辑访问黑白名单",type = OperateType.update)
    public R visitStateEdit(VisitStateEditRequest request) {
        BrowserVisitSafe browserVisitSafe = new BrowserVisitSafe();
        browserVisitSafe.setId(request.getId());
        browserVisitSafe.setState(request.getState());
        browserVisitSafe.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        browserVisitSafeMapper.updateById(browserVisitSafe);
        //删除缓存
        CacheUtils.remove(SAFE);
        return R.success();
    }

    @Override
    public R visitList(VisitPageRequest request) {
        Page<BrowserVisitSafe> browserVisitSafePage = new Page<>();
        browserVisitSafePage.setSize(request.getOffset());
        browserVisitSafePage.setCurrent(request.getCurrent());
        QueryWrapper<BrowserVisitSafe> browserVisitSafeQueryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(request.getForbiddenUrl())){
            browserVisitSafeQueryWrapper.like("FORBIDDEN_URL",request.getForbiddenUrl());
        }
        if(StringUtils.isNotEmpty(request.getUrlDescription())){
            browserVisitSafeQueryWrapper.like("URL_DESCRIPTION",request.getForbiddenUrl());
        }
        if (request.getState() != null) {
            browserVisitSafeQueryWrapper.eq("STATE", request.getState());
        }
        browserVisitSafeQueryWrapper.orderByDesc("CREATE_TIME");
        Page<BrowserVisitSafe> result = browserVisitSafeMapper.selectPage(browserVisitSafePage, browserVisitSafeQueryWrapper);
        return R.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除访问黑名单",type = OperateType.delete)
    public R visitDelete(String id) {
        browserVisitSafeMapper.deleteById(id);
        //删除缓存
        CacheUtils.remove(SAFE);
        return R.success();
    }

    @Override
    public R visitDetail(String id) {
        BrowserVisitSafe browserVisitSafe = browserVisitSafeMapper.selectById(id);
        return R.success(browserVisitSafe);
    }


}
