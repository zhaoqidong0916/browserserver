package com.ht.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.entity.BrowserCompatibleConfig;
import com.ht.common.page.R;
import com.ht.common.request.compatible.CompatibleAddRequest;
import com.ht.common.request.compatible.CompatiblePageRequest;
import com.ht.common.request.compatible.CompatibleUpdateRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-06
 */
public interface IBrowserCompatibleConfigService extends IService<BrowserCompatibleConfig> {

    R compatibleAdd(CompatibleAddRequest request);

    R compatibleUpdate(CompatibleUpdateRequest request);

    R compatibleList(CompatiblePageRequest request);

    R compatibleDelete(String id);

    R compatibleDetail(String id);
}
