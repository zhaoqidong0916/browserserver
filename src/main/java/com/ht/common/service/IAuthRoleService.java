package com.ht.common.service;

import com.ht.common.entity.AuthRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.page.R;
import com.ht.common.request.system.role.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
public interface IAuthRoleService extends IService<AuthRole> {

    R roleAdd(RolesAddRequest request);

    R roleUpdate(RolesUpdateRequest request);

    R roleList(RolesPageRequest request);

    R roleEnable(RolesEnableRequest request);

    R roleDelete(String id);

    R roleDetail(String id);

    R roleResourceAdd(RoleResourceAddRequest request);

    R roleResourceList(String roleId);
}
