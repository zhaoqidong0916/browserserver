package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.dao.BrowserApplicationResourcesMapper;
import com.ht.common.entity.BrowserApplicationResources;
import com.ht.common.page.R;
import com.ht.common.request.application.resource.ApplicationResourceAddRequest;
import com.ht.common.request.application.resource.ApplicationResourcePageRequest;
import com.ht.common.request.application.resource.ApplicationResourceUpdateRequest;
import com.ht.common.service.IBrowserApplicationResourcesService;
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

import static com.ht.common.constant.Constant.APPLICATION;
import static com.ht.common.constant.Constant.SAFE;
import static com.ht.common.enums.ResponseEnum.UN_SUPPORT_FORMAT;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-10
 */
@Service
public class BrowserApplicationResourcesServiceImpl extends ServiceImpl<BrowserApplicationResourcesMapper, BrowserApplicationResources> implements IBrowserApplicationResourcesService {

    @Autowired
    private BrowserApplicationResourcesMapper applicationResourcesMapper;

    @Autowired
    private UploadUtils uploadUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "添加首页背景资源", type = OperateType.add)
    public R resourceAdd(ApplicationResourceAddRequest request) {
        BrowserApplicationResources browserApplicationResources = new BrowserApplicationResources();
        browserApplicationResources.setDownloadUrl(request.getDownloadUrl());
        browserApplicationResources.setId(UUIDUtil.getUUID());
        browserApplicationResources.setResourcesType(request.getResourcesType());
        browserApplicationResources.setResourcesName(request.getResourcesName());
        browserApplicationResources.setResourcesDescribe(request.getResourcesDescribe());
        String resourcesTypeName = "";
        if (request.getResourcesType() == 1) {
            resourcesTypeName = "文本";
        } else if (request.getResourcesType() == 2) {
            resourcesTypeName = "图片";
        } else {
            resourcesTypeName = "视频";
        }
        browserApplicationResources.setResourcesTypeName(resourcesTypeName);
        browserApplicationResources.setCreateTime(new Timestamp(System.currentTimeMillis()));
        applicationResourcesMapper.insert(browserApplicationResources);
        //删除缓存
        CacheUtils.remove(APPLICATION);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "更新首页背景资源", type = OperateType.update)
    public R resourceUpdate(ApplicationResourceUpdateRequest request) {
        BrowserApplicationResources browserApplicationResources = new BrowserApplicationResources();
        browserApplicationResources.setId(request.getId());
        browserApplicationResources.setResourcesName(request.getResourcesName());
        browserApplicationResources.setResourcesDescribe(request.getResourcesDescribe());
        browserApplicationResources.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if (StringUtils.isNotEmpty(request.getDownloadUrl())) {
            browserApplicationResources.setDownloadUrl(request.getDownloadUrl());
        }
        applicationResourcesMapper.updateById(browserApplicationResources);
        //删除缓存
        CacheUtils.remove(APPLICATION);
        return R.success();
    }

    @Override
    public R resourceList(ApplicationResourcePageRequest request) {
        Page<BrowserApplicationResources> page = new Page<>();
        page.setCurrent(request.getCurrent());
        page.setSize(request.getOffset());
        QueryWrapper<BrowserApplicationResources> wrapper = new QueryWrapper<>();
        if (request.getResourcesType() != null) {
            wrapper.eq("RESOURCES_TYPE", request.getResourcesType());
        }
        if (StringUtils.isNotEmpty(request.getResourcesName())) {
            wrapper.like("RESOURCES_NAME", request.getResourcesName());
        }
        wrapper.orderByDesc("CREATE_TIME");
        IPage<BrowserApplicationResources> employeeIPage = applicationResourcesMapper.selectPage(page, wrapper);
        return R.success(employeeIPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除首页背景资源", type = OperateType.delete)
    public R resourceDelete(String id) {
        applicationResourcesMapper.deleteById(id);
        //删除缓存
        CacheUtils.remove(APPLICATION);
        return R.success();
    }

    @Override
    public R resourceDetail(String id) {
        return R.success(applicationResourcesMapper.selectById(id));
    }

    @Override
    public R resourceUpload(MultipartFile file) {
        if (!HandleUtils.isBanners(file.getOriginalFilename())) {
            return R.result(UN_SUPPORT_FORMAT);
        }
        //文件上传，返回上传地址
        Map<String, String> upload = uploadUtils.upload(file, file.getOriginalFilename(), false);
        Map<String, String> map = new HashMap<>(4);
        map.put("downloadUrl", upload.get("downloadUrl"));
        return R.success(map);

    }

}
