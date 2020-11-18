package com.ht.common.service;

import com.ht.common.entity.BrowserPluginUpgrade;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.page.R;
import com.ht.common.request.upgrade.plugins.BrowserPluginUpgradeAddRequest;
import com.ht.common.request.upgrade.plugins.BrowserPluginUpgradePageRequest;
import com.ht.common.request.upgrade.plugins.BrowserPluginUpgradeUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-11
 */
public interface IBrowserPluginUpgradeService extends IService<BrowserPluginUpgrade> {

    R pluginAdd(BrowserPluginUpgradeAddRequest request);

    R pluginUpdate(BrowserPluginUpgradeUpdateRequest request);

    R pluginList(BrowserPluginUpgradePageRequest request);

    R pluginDelete(String id);

    R pluginDetail(String id);

    R pluginUpload(MultipartFile file);

    R pluginTypeListForUpgrade();

    R pluginAddUpdate(BrowserPluginUpgradeAddRequest request);
}
