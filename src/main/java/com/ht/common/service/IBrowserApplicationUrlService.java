package com.ht.common.service;

import com.ht.common.entity.BrowserApplicationUrl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.page.R;
import com.ht.common.request.application.url.ApplicationUrlAddRequest;
import com.ht.common.request.application.url.ApplicationUrlPageRequest;
import com.ht.common.request.application.url.ApplicationUrlUpdateRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-11
 */
public interface IBrowserApplicationUrlService extends IService<BrowserApplicationUrl> {

    R urlAdd(ApplicationUrlAddRequest request);

    R urlUpdate(ApplicationUrlUpdateRequest request);

    R urlList(ApplicationUrlPageRequest request);

    R urlDelete(String id);

    R urlDetail(String id);
}
