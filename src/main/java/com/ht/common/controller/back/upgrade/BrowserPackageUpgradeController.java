package com.ht.common.controller.back.upgrade;


import com.ht.common.page.R;
import com.ht.common.request.upgrade.browser.BrowserUpgradeAddRequest;
import com.ht.common.request.upgrade.browser.BrowserUpgradePageRequest;
import com.ht.common.request.upgrade.browser.BrowserUpgradeUpdateRequest;
import com.ht.common.request.upgrade.browser.BrowserUpgradeVersionResetRequest;
import com.ht.common.service.IAuthUserService;
import com.ht.common.service.IBrowserPackageUpgradeService;
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
 * 浏览器升级包
 *
 * @author yakun.shi
 * @since 2020-03-11
 */
@RestController
@RequestMapping("upgrade")
@Api(tags = "升级功能--浏览器升级管理")
public class BrowserPackageUpgradeController {

    @Autowired
    private IBrowserPackageUpgradeService browserPackageUpgradeService;
    @Autowired
    private IAuthUserService authUserService;
    @PostMapping("browser/add")
    @ApiOperation(value = "添加浏览器升级包")
    public R browserAdd(@Valid @RequestBody BrowserUpgradeAddRequest request) {
        return browserPackageUpgradeService.browserAdd(request);
    }

    @PostMapping("browser/update")
    @ApiOperation(value = "修改浏览器升级包")
    public R browserUpdate(@Valid @RequestBody BrowserUpgradeUpdateRequest request) {
        return browserPackageUpgradeService.browserUpdate(request);
    }

    @PostMapping("browser/list")
    @ApiOperation(value = "列表")
    public R browserList(@RequestBody BrowserUpgradePageRequest request,ServletRequest request1) {

        boolean isFlag = this.authUserService.autoFlag(request1);
        if (isFlag) {
          return this.browserPackageUpgradeService.browserList(request);
        }
        return null;
    }

    @PostMapping("browser/delete/{id}")
    @ApiOperation(value = "删除浏览器升级包")
    public R browserDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserPackageUpgradeService.browserDelete(id);
    }

    @GetMapping("browser/detail/{id}")
    @ApiOperation(value = "详情")
    public R browserDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserPackageUpgradeService.browserDetail(id);
    }

    @PostMapping("browser/upload")
    @ApiOperation(value = "上传")
    public R browserUpload(@RequestParam("file") MultipartFile file) {
        return browserPackageUpgradeService.browserUpload(file);
    }


    @PostMapping("browser/reset")
    @ApiOperation(value = "历史版本回滚")
    public R browserReset(@RequestBody BrowserUpgradeVersionResetRequest request) {
        return browserPackageUpgradeService.browserReset(request);
    }



    @GetMapping("browser/history/{browserType}")
    @ApiOperation(value = "历史版本")
    public R browseHistory(@PathVariable Integer browserType) {
        return browserPackageUpgradeService.browseHistory(browserType);
    }
}

