package com.ht.common.controller.back.system.user;


import com.ht.common.page.R;
import com.ht.common.request.system.user.ResetPasswordRequest;
import com.ht.common.request.system.user.UserPageRequest;
import com.ht.common.request.system.user.UserRegisterRequest;
import com.ht.common.request.system.user.UserUpdateRequest;
import com.ht.common.service.IAuthUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
@RestController
@RequestMapping("authUser")
@Validated
@Api(tags = {"系统管理--用户功能"})
public class AuthUserController {

    @Autowired
    private IAuthUserService authUserService;

    @PostMapping("register")
    @ApiOperation(value = "注册用户")
    public R register(@Valid @RequestBody UserRegisterRequest request) {
        return authUserService.register(request);
    }

    @PostMapping("update")
    @ApiOperation(value = "修改用户信息")
    public R userUpdate(@Valid @RequestBody UserUpdateRequest request) {
        return authUserService.userUpdate(request);
    }

    @PostMapping("list")
    @ApiOperation(value = "用户列表")
    public R userList(@RequestBody UserPageRequest request) {
        return authUserService.userList(request);
    }

    @PostMapping("delete/{userId}")
    @ApiOperation(value = "用户删除")
    public R userDelete(@NotBlank(message = "用户id不能为空") @PathVariable String userId) {
        return authUserService.userDelete(userId);
    }

    @GetMapping("detail/{userId}")
    @ApiOperation(value = "用户详情")
    public R userDetail(@NotBlank(message = "用户id不能为空") @PathVariable String userId) {
        return authUserService.userDetail(userId);
    }

    @PostMapping("reset/password")
    @ApiOperation(value = "用户重置密码")
    public R resetPassword(@RequestBody ResetPasswordRequest request, HttpServletRequest httpServletRequest) {
        return authUserService.resetPassword(request,httpServletRequest);
    }

    @GetMapping("role/list")
    @ApiOperation(value = "用户添加角色--选取角色列表")
    public R selectRoleListForUser(ServletRequest request) {
    	 boolean isFlag = this.authUserService.autoFlag(request);
    	    if (isFlag) {
    	      return this.authUserService.selectRoleListForUser();
    	    }
    	    return null;
    }

}

