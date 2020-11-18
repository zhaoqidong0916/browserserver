package com.ht.common.controller.back.system.resource;


import com.ht.common.page.R;
import com.ht.common.request.system.resources.ResourcesAddRequest;
import com.ht.common.request.system.resources.ResourcesEnableRequest;
import com.ht.common.request.system.resources.ResourcesPageRequest;
import com.ht.common.request.system.resources.ResourcesUpdateRequest;
import com.ht.common.service.IAuthResourcesService;
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
 * 前端控制器
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
@RestController
@RequestMapping("system")
@Validated
@Api(tags = "系统管理--菜单管理")
public class AuthResourcesController {

    @Autowired
    private IAuthResourcesService resourcesService;
    @Autowired
    private IAuthUserService authUserService;
    @PostMapping("resource/add")
    @ApiOperation(value = "添加菜单")
    public R resourceAdd(@Valid @RequestBody ResourcesAddRequest request) {
        return resourcesService.resourceAdd(request);
    }

    @PostMapping("resource/update")
    @ApiOperation(value = "修改菜单")
    public R resourceUpdate(@Valid @RequestBody ResourcesUpdateRequest request) {
        return resourcesService.resourceUpdate(request);
    }

    @PostMapping("resource/list")
    @ApiOperation(value = "一级菜单列表")
    public R resourceList(@RequestBody ResourcesPageRequest request,ServletRequest request1) {
    	 boolean isFlag = this.authUserService.autoFlag(request1);
    	    if (isFlag) {
    	      return this.resourcesService.resourceList(request);
    	    }
    	    return null;
    }

    @GetMapping("resource/sonList/{parentName}")
    @ApiOperation(value = "二级菜单列表")
    public R resourceSonList(@PathVariable String parentName) {
        return resourcesService.resourceSonList(parentName);
    }

    @PostMapping("resource/enable")
    @ApiOperation(value = "启用或者禁用菜单")
    public R resourceEnable(@RequestBody ResourcesEnableRequest request) {
        return resourcesService.resourceEnable(request);
    }


    @PostMapping("resource/delete/{id}")
    @ApiOperation(value = "删除菜单")
    public R resourceDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return resourcesService.resourceDelete(id);
    }

    @GetMapping("resource/detail/{id}")
    @ApiOperation(value = "详情")
    public R resourceDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return resourcesService.resourceDetail(id);
    }


    @GetMapping("left_data/{userId}")
    @ApiOperation(value = "左侧菜单栏")
    public R LeftSideBar(@PathVariable String userId) {
        return resourcesService.LeftSideBar(userId);
    }
}

