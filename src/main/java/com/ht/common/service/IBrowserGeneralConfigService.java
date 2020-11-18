package com.ht.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.entity.BrowserGeneralConfig;
import com.ht.common.page.R;
import com.ht.common.request.general.GeneralUpdateRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-06
 */
public interface IBrowserGeneralConfigService extends IService<BrowserGeneralConfig> {

    R generalUpdate(GeneralUpdateRequest request);

    R generalList();

    R GeneralDetail(String id);
}
