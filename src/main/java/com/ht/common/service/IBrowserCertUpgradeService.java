package com.ht.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.entity.BrowserCertUpgrade;
import com.ht.common.page.R;
import com.ht.common.request.upgrade.cert.upgrade.CertUpgradeAddRequest;
import com.ht.common.request.upgrade.cert.upgrade.CertUpgradePageRequest;
import com.ht.common.request.upgrade.cert.upgrade.CertUpgradeUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-23
 */
public interface IBrowserCertUpgradeService extends IService<BrowserCertUpgrade> {

    R certAdd(CertUpgradeAddRequest request);

    R certUpdate(CertUpgradeUpdateRequest request);

    R certList(CertUpgradePageRequest request);

    R certDelete(String id);

    R certDetail(String id);

    R certUpload(MultipartFile file);

    R certTypeListForUpgrade();

    R certAddUpdate(CertUpgradeAddRequest request);
}
