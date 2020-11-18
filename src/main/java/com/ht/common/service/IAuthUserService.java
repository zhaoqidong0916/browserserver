package com.ht.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.entity.AuthUser;
import com.ht.common.page.R;
import com.ht.common.request.system.user.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
public interface IAuthUserService extends IService<AuthUser> {

    R login(LoginRequest request);

    R logout(HttpServletRequest request);

    R register(UserRegisterRequest request);

    R userUpdate(UserUpdateRequest request);

    R userDelete(String userId);

    R userDetail(String userId);

    R userList(UserPageRequest request);

    R resetPassword(ResetPasswordRequest request,HttpServletRequest httpServletRequest);

    R selectRoleListForUser();
    boolean autoFlag(ServletRequest paramServletRequest);
}
