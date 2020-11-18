package com.ht.common.controller.back.upgrade;


import com.ht.common.page.R;
import com.ht.common.request.upgrade.plugins.BrowserPluginUpgradeAddRequest;
import com.ht.common.request.upgrade.plugins.BrowserPluginUpgradePageRequest;
import com.ht.common.request.upgrade.plugins.BrowserPluginUpgradeUpdateRequest;
import com.ht.common.service.IAuthUserService;
import com.ht.common.service.IBrowserPluginUpgradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-11
 */
@RestController
@RequestMapping("upgrade")
@Api(tags = "升级功能--控件升级管理")
public class BrowserPluginUpgradeController {

    @Autowired
    private IBrowserPluginUpgradeService browserPluginUpgradeService;
    @Autowired
    private IAuthUserService authUserService;
    @PostMapping("plugin/add")
    @ApiOperation(value = "添加控件安装包")
    public R pluginAdd(@Valid @RequestBody BrowserPluginUpgradeAddRequest request) {
        return browserPluginUpgradeService.pluginAdd(request);
    }


    @PostMapping("plugin/add/update")
    @ApiOperation(value = "添加控件安装包,相同控件类型更新接口")
    public R pluginAddUpdate(@Valid @RequestBody BrowserPluginUpgradeAddRequest request) {
        return browserPluginUpgradeService.pluginAddUpdate(request);
    }

    @PostMapping("plugin/update")
    @ApiOperation(value = "修改控件安装包")
    public R pluginUpdate(@Valid @RequestBody BrowserPluginUpgradeUpdateRequest request) {
        return browserPluginUpgradeService.pluginUpdate(request);
    }

    @PostMapping("plugin/list")
    @ApiOperation(value = "列表")
    public R pluginList(@RequestBody BrowserPluginUpgradePageRequest request,ServletRequest request1) {
    	  boolean isFlag = this.authUserService.autoFlag(request1);
    	    if (isFlag) {
    	      return this.browserPluginUpgradeService.pluginList(request);
    	    }
    	    return null;
    }

    @PostMapping("plugin/delete/{id}")
    @ApiOperation(value = "删除控件安装包")
    public R pluginDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserPluginUpgradeService.pluginDelete(id);
    }

    @GetMapping("plugin/detail/{id}")
    @ApiOperation(value = "详情")
    public R pluginDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserPluginUpgradeService.pluginDetail(id);
    }

    @PostMapping("plugin/upload")
    @ApiOperation(value = "上传")
    public R pluginUpload(@RequestParam("file") MultipartFile file) {
        return browserPluginUpgradeService.pluginUpload(file);
    }

    @GetMapping("pluginType/list")
    @ApiOperation(value = "控件升级--控件类型列表")
    public R pluginTypeListForUpgrade() {
        return browserPluginUpgradeService.pluginTypeListForUpgrade();
    }
}

