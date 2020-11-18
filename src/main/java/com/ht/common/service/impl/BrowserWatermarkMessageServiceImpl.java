package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.dao.BrowserWatermarkConfigMapper;
import com.ht.common.entity.BrowserWatermarkConfig;
import com.ht.common.page.R;
import com.ht.common.request.watermark.WatermarkPageRequest;
import com.ht.common.request.watermark.WatermarkUpdateRequest;
import com.ht.common.service.IBrowserWatermarkMessageService;
import com.ht.common.utils.CacheUtils;
import com.ht.common.utils.HandleUtils;
import com.ht.common.utils.upload.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ht.common.constant.Constant.STRATEGY;
import static com.ht.common.enums.ResponseEnum.UN_SUPPORT_FORMAT;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-06
 */
@Service
public class BrowserWatermarkMessageServiceImpl extends ServiceImpl<BrowserWatermarkConfigMapper, BrowserWatermarkConfig> implements IBrowserWatermarkMessageService {

    @Autowired
    private BrowserWatermarkConfigMapper browserWatermarkConfigMapper;

    @Autowired
    private UploadUtils uploadUtils;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public R watermarkUpdate(WatermarkUpdateRequest request) {
        BrowserWatermarkConfig browserWatermarkConfig = new BrowserWatermarkConfig();
        browserWatermarkConfig.setId(request.getId());
        browserWatermarkConfig.setTransparency(request.getTransparency());
        browserWatermarkConfig.setWatermarkName(request.getWatermarkName());
        browserWatermarkConfig.setDownloadUrl(request.getDownloadUrl());
        browserWatermarkConfig.setWatermarkState(request.getWatermarkState());
        browserWatermarkConfigMapper.updateById(browserWatermarkConfig);
        //删除缓存
        CacheUtils.remove(STRATEGY);
        return R.success();
    }

    @Override
    public R watermarkList(WatermarkPageRequest request) {
        List<BrowserWatermarkConfig> browserWatermarkConfigs = browserWatermarkConfigMapper.selectList(new QueryWrapper<>());
        return R.success(browserWatermarkConfigs);
    }

    @Override
    public R watermarkDetail(String id) {
        return R.success(browserWatermarkConfigMapper.selectById(id));
    }

    @Override
    public R watermarkUpload(MultipartFile file) {
        if (!HandleUtils.isPhoto(file.getOriginalFilename())) {
            return R.result(UN_SUPPORT_FORMAT);
        }
        //文件上传，返回上传地址
        Map<String, String> upload = uploadUtils.upload(file, file.getOriginalFilename(), false);
        Map<String, String> map = new HashMap<>(4);
        map.put("downloadUrl", upload.get("downloadUrl"));
        return R.success(map);
    }
}
