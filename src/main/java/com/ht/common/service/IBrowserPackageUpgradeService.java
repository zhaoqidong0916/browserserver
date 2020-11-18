package com.ht.common.service;

import com.ht.common.entity.BrowserPackageUpgrade;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.page.R;
import com.ht.common.request.upgrade.browser.BrowserUpgradeAddRequest;
import com.ht.common.request.upgrade.browser.BrowserUpgradePageRequest;
import com.ht.common.request.upgrade.browser.BrowserUpgradeUpdateRequest;
import com.ht.common.request.upgrade.browser.BrowserUpgradeVersionResetRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-11
 */
public interface IBrowserPackageUpgradeService extends IService<BrowserPackageUpgrade> {

    R browserAdd(BrowserUpgradeAddRequest request);

    R browserUpdate(BrowserUpgradeUpdateRequest request);

    R browserList(BrowserUpgradePageRequest request);

    R browserDelete(String id);

    R browserDetail(String id);

    R browserUpload(MultipartFile file);

    R browseHistory(Integer browserType);

    R browserReset(BrowserUpgradeVersionResetRequest request);
}
