package com.ht.common.controller.back.system.role;


import com.ht.common.page.R;
import com.ht.common.request.system.role.*;
import com.ht.common.service.IAuthRoleService;
import com.ht.common.service.IAuthUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
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
@RequestMapping("system")
@Validated
@Api(tags = "系统管理--角色管理")
public class AuthRoleController {

    @Autowired
    private IAuthRoleService authRoleService;
    @Autowired
    private IAuthUserService authUserService;
    @PostMapping("role/add")
    @ApiOperation(value = "添加角色")
    public R roleAdd(@Valid @RequestBody RolesAddRequest request) {
        return authRoleService.roleAdd(request);
    }

    @PostMapping("role/update")
    @ApiOperation(value = "修改角色")
    public R roleUpdate(@Valid @RequestBody RolesUpdateRequest request) {
        return authRoleService.roleUpdate(request);
    }

    @PostMapping("role/list")
    @ApiOperation(value = "列表")
    public R roleList(@RequestBody RolesPageRequest request,ServletRequest request1) {
    	  boolean isFlag = this.authUserService.autoFlag(request1);
    	    if (isFlag) {
    	      return this.authRoleService.roleList(request);
    	    }
    	    return null;
    }

    @PostMapping("role/enable")
    @ApiOperation(value = "启用或者禁用角色")
    public R roleEnable(@RequestBody RolesEnableRequest request) {
        return authRoleService.roleEnable(request);
    }


    @PostMapping("role/delete/{id}")
    @ApiOperation(value = "删除角色")
    public R roleDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return authRoleService.roleDelete(id);
    }

    @GetMapping("role/detail/{id}")
    @ApiOperation(value = "详情")
    public R roleDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return authRoleService.roleDetail(id);
    }

    @PostMapping("role/resourceAdd")
    @ApiOperation(value = "角色--页面权限添加")
    public R roleResourceAdd(@RequestBody RoleResourceAddRequest request) {
        return authRoleService.roleResourceAdd(request);
    }

    @GetMapping("role/resourceList/{roleId}")
    @ApiOperation(value = "角色--页面权限列表")
    public R roleResourceList(@NotBlank(message = "id不能为空") @PathVariable String roleId) {
        return authRoleService.roleResourceList(roleId);
    }
}

