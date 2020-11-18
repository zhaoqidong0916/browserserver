package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.dao.BrowserApplicationIconsMapper;
import com.ht.common.entity.BrowserApplicationIcons;
import com.ht.common.page.R;
import com.ht.common.service.IBrowserApplicationIconsService;
import com.ht.common.utils.HandleUtils;
import com.ht.common.utils.UUIDUtil;
import com.ht.common.utils.upload.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.ht.common.enums.ResponseEnum.UN_SUPPORT_FORMAT;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-04-07
 */
@Service
public class BrowserApplicationIconsServiceImpl extends ServiceImpl<BrowserApplicationIconsMapper, BrowserApplicationIcons> implements IBrowserApplicationIconsService {

    @Autowired
    private BrowserApplicationIconsMapper browserApplicationIconsMapper;

    @Autowired
    private UploadUtils uploadUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R iconUpload(MultipartFile[] files) {
        boolean flag = false;
        for (int i = 0; i < files.length; i++) {
            boolean photo = HandleUtils.isPhoto(files[i].getOriginalFilename());
            if (!photo) {
                flag = true;
            }
        }
        if (flag) {
            return R.result(UN_SUPPORT_FORMAT);
        }

        for (int i = 0; i < files.length; i++) {
            //文件上传，返回上传地址
            MultipartFile file = files[i];
            Map<String, String> upload = uploadUtils.upload(file, file.getOriginalFilename(), false);
            BrowserApplicationIcons browserApplicationIcons = new BrowserApplicationIcons();
            browserApplicationIcons.setId(UUIDUtil.getUUID());
            browserApplicationIcons.setUrl(upload.get("downloadUrl"));
            browserApplicationIconsMapper.insert(browserApplicationIcons);
        }
        return R.success();
    }

    @Override
    public R iconList() {
        List<BrowserApplicationIcons> browserApplicationIcons = browserApplicationIconsMapper.selectList(new QueryWrapper<>());
        return R.success(browserApplicationIcons);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R iconDelete(String id) {
        browserApplicationIconsMapper.deleteById(id);
        return R.success();
    }
}
