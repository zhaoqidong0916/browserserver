package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.dao.BrowserApplicationUrlMapper;
import com.ht.common.entity.BrowserApplicationUrl;
import com.ht.common.page.R;
import com.ht.common.request.application.url.ApplicationUrlAddRequest;
import com.ht.common.request.application.url.ApplicationUrlPageRequest;
import com.ht.common.request.application.url.ApplicationUrlUpdateRequest;
import com.ht.common.service.IBrowserApplicationUrlService;
import com.ht.common.utils.CacheUtils;
import com.ht.common.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.ht.common.constant.Constant.APPLICATION;
import static com.ht.common.constant.Constant.APPLICATION_URL;
import static com.ht.common.enums.ResponseEnum.DATA_EXIST;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-11
 */
@Service
public class BrowserApplicationUrlServiceImpl extends ServiceImpl<BrowserApplicationUrlMapper, BrowserApplicationUrl> implements IBrowserApplicationUrlService {

    @Autowired
    private BrowserApplicationUrlMapper applicationUrlMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "添加首页背景url链接", type = OperateType.add)
    public R urlAdd(ApplicationUrlAddRequest request) {
        QueryWrapper<BrowserApplicationUrl> wrapper = new QueryWrapper<BrowserApplicationUrl>().eq("URL_ADDRESS", request.getUrlAddress());
        if (applicationUrlMapper.selectCount(wrapper) > 0) {
            return R.result(DATA_EXIST);
        }
        BrowserApplicationUrl browserApplicationUrl = new BrowserApplicationUrl();
        browserApplicationUrl.setUrlAddress(request.getUrlAddress());
        browserApplicationUrl.setId(UUIDUtil.getUUID());
        browserApplicationUrl.setUrlDescribe(request.getUrlDescribe());
        browserApplicationUrl.setUrlName(request.getUrlName());
        browserApplicationUrl.setIconUrl(request.getIconUrl());
        browserApplicationUrl.setCreateTime(new Timestamp(System.currentTimeMillis()));
        applicationUrlMapper.insert(browserApplicationUrl);
        //删除缓存
        CacheUtils.remove(APPLICATION_URL);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "更新首页背景url链接", type = OperateType.update)
    public R urlUpdate(ApplicationUrlUpdateRequest request) {
        BrowserApplicationUrl browserApplicationUrl = new BrowserApplicationUrl();
        if (StringUtils.isNotEmpty(request.getUrlAddress())) {
            QueryWrapper<BrowserApplicationUrl> wrapper = new QueryWrapper<BrowserApplicationUrl>().eq("URL_ADDRESS", request.getUrlAddress());
            if (applicationUrlMapper.selectCount(wrapper) > 0) {
                return R.result(DATA_EXIST);
            }
            browserApplicationUrl.setUrlAddress(request.getUrlAddress());
        }
        browserApplicationUrl.setId(request.getId());
        browserApplicationUrl.setUrlDescribe(request.getUrlDescribe());
        browserApplicationUrl.setUrlName(request.getUrlName());
        browserApplicationUrl.setIconUrl(request.getIconUrl());
        browserApplicationUrl.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        applicationUrlMapper.updateById(browserApplicationUrl);
        //删除缓存
        CacheUtils.remove(APPLICATION_URL);
        return R.success();
    }

    @Override
    public R urlList(ApplicationUrlPageRequest request) {
        Page<BrowserApplicationUrl> page = new Page<>();
        page.setCurrent(request.getCurrent());
        page.setSize(request.getOffset());
        QueryWrapper<BrowserApplicationUrl> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getUrlName())) {
            wrapper.like("URL_NAME", request.getUrlName());
        }
        if (StringUtils.isNotEmpty(request.getUrlAddress())) {
            wrapper.like("URL_ADDRESS", request.getUrlAddress());
        }
        wrapper.orderByDesc("CREATE_TIME");
        IPage<BrowserApplicationUrl> browser = applicationUrlMapper.selectPage(page, wrapper);
        return R.success(browser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除首页背景url链接", type = OperateType.delete)
    public R urlDelete(String id) {
        applicationUrlMapper.deleteById(id);
        //删除缓存
        CacheUtils.remove(APPLICATION_URL);
        return R.success();
    }

    @Override
    public R urlDetail(String id) {
        return R.success(applicationUrlMapper.selectById(id));
    }
}
