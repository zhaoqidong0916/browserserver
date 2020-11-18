package com.ht.common.controller.back.config;


import com.ht.common.page.R;
import com.ht.common.request.compatible.CompatibleAddRequest;
import com.ht.common.request.compatible.CompatiblePageRequest;
import com.ht.common.request.compatible.CompatibleUpdateRequest;
import com.ht.common.service.IAuthUserService;
import com.ht.common.service.IBrowserCompatibleConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

/**
 * 浏览器兼容配置
 * @author yakun.shi
 * @since 2020-03-06
 */
@RestController
@RequestMapping("config")
@Validated
@Api(tags = {"策略管理--浏览器兼容配置"})
public class  BrowserCompatibleConfigController {

    @Autowired
    private IBrowserCompatibleConfigService browserCompatibleConfigService;
    @Autowired
    private IAuthUserService authUserService;
    @PostMapping("compatible/add")
    @ApiOperation(value = "添加浏览器兼容配置")
    public R compatibleAdd(@Valid @RequestBody CompatibleAddRequest request) {
        return browserCompatibleConfigService.compatibleAdd(request);
    }

    @PostMapping("compatible/update")
    @ApiOperation(value = "修改浏览器兼容配置")
    public R compatibleUpdate(@Valid @RequestBody CompatibleUpdateRequest request) {
        return browserCompatibleConfigService.compatibleUpdate(request);
    }

    @PostMapping("compatible/list")
    @ApiOperation(value = "列表")
    public R compatibleList(@RequestBody CompatiblePageRequest request,ServletRequest request1) {
    	  boolean isFlag = this.authUserService.autoFlag(request1);
    	    if (isFlag) {
    	      return this.browserCompatibleConfigService.compatibleList(request);
    	    }
    	    return null;
    }

    @PostMapping("compatible/delete/{id}")
    @ApiOperation(value = "删除浏览器兼容配置")
    public R compatibleDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserCompatibleConfigService.compatibleDelete(id);
    }

    @GetMapping("compatible/detail/{id}")
    @ApiOperation(value = "详情")
    public R compatibleDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserCompatibleConfigService.compatibleDetail(id);
    }

}

