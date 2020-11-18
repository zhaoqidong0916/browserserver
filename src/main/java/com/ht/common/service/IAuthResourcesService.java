package com.ht.common.service;

import com.ht.common.entity.AuthResources;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.page.R;
import com.ht.common.request.system.resources.ResourcesAddRequest;
import com.ht.common.request.system.resources.ResourcesEnableRequest;
import com.ht.common.request.system.resources.ResourcesPageRequest;
import com.ht.common.request.system.resources.ResourcesUpdateRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
public interface IAuthResourcesService extends IService<AuthResources> {

    R resourceAdd(ResourcesAddRequest request);

    R resourceUpdate(ResourcesUpdateRequest request);

    R resourceList(ResourcesPageRequest request);

    R resourceEnable(ResourcesEnableRequest request);

    R resourceDelete(String id);

    R resourceDetail(String id);

    R LeftSideBar(String userId);

    R resourceSonList(String parentName);
}
