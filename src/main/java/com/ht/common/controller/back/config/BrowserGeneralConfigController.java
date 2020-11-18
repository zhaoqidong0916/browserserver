package com.ht.common.controller.back.config;


import com.ht.common.page.R;
import com.ht.common.request.general.GeneralUpdateRequest;
import com.ht.common.service.IAuthUserService;
import com.ht.common.service.IBrowserGeneralConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

/**
 * 浏览器通用配置
 * @author yakun.shi
 * @since 2020-03-06
 */
@RestController
@RequestMapping("config")
@Validated
@Api(tags = {"策略管理--浏览器通用配置"})
public class BrowserGeneralConfigController {
    @Autowired
    private IBrowserGeneralConfigService browserGeneralConfigService;
    @Autowired
    private IAuthUserService authUserService;
    @PostMapping("general/update")
    @ApiOperation(value = "修改浏览器通用配置")
    public R generalUpdate(@Valid @RequestBody GeneralUpdateRequest request) {
        return browserGeneralConfigService.generalUpdate(request);
    }

    @PostMapping("general/list")
    @ApiOperation(value = "列表")
    public R generalList(ServletRequest request) {
    	 boolean isFlag = this.authUserService.autoFlag(request);
    	    if (isFlag) {
    	      return this.browserGeneralConfigService.generalList();
    	    }
    	    return null;
    }

    @GetMapping("general/detail/{id}")
    @ApiOperation(value = "详情")
    public R generalDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserGeneralConfigService.GeneralDetail(id);
    }
}

