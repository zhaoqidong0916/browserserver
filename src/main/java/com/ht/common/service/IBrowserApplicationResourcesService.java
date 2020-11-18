package com.ht.common.service;

import com.ht.common.entity.BrowserApplicationResources;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.page.R;
import com.ht.common.request.application.resource.ApplicationResourceAddRequest;
import com.ht.common.request.application.resource.ApplicationResourcePageRequest;
import com.ht.common.request.application.resource.ApplicationResourceUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-10
 */
public interface IBrowserApplicationResourcesService extends IService<BrowserApplicationResources> {

    R resourceAdd(ApplicationResourceAddRequest request);

    R resourceUpdate(ApplicationResourceUpdateRequest request);

    R resourceList(ApplicationResourcePageRequest request);

    R resourceDelete(String id);

    R resourceDetail(String id);

    R resourceUpload(MultipartFile file);

}
