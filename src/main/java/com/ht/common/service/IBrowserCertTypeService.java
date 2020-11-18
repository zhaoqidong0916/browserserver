package com.ht.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.entity.BrowserCertType;
import com.ht.common.page.R;
import com.ht.common.request.upgrade.cert.type.CertTypeAddRequest;
import com.ht.common.request.upgrade.cert.type.CertTypePageReqeust;
import com.ht.common.request.upgrade.cert.type.CertTypeUpdateRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-23
 */
public interface IBrowserCertTypeService extends IService<BrowserCertType> {

    R certTypeAdd(CertTypeAddRequest request);

    R certTypeUpdate(CertTypeUpdateRequest request);

    R certTypeList(CertTypePageReqeust request);

    R certTypeDelete(String id);

    R certTypeDetail(String id);
}
