package com.ht.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.entity.BrowserApplicationIcons;
import com.ht.common.page.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-04-07
 */
public interface IBrowserApplicationIconsService extends IService<BrowserApplicationIcons> {

    R iconUpload(MultipartFile[] file);

    R iconList();

    R iconDelete(String id);
}
