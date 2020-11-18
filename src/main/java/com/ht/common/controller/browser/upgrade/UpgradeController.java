package com.ht.common.controller.browser.upgrade;

import com.ht.common.controller.browser.upgrade.request.UpgradeRequest;
import com.ht.common.page.R;
import com.ht.common.service.IUpgradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * @description: 升级controller
 * @author: yaKun.shi
 * @create: 2020-03-16 09:09
 **/
@RestController
@RequestMapping("upgrade")
@Api(tags = "浏览器服务接口--浏览器升级接口")
@Validated
public class UpgradeController {
    @Autowired
    private IUpgradeService upgradeService;

    @GetMapping("browser")
    @ApiOperation("浏览器升级接口")
    public R browserUpgrade(@RequestParam @NotEmpty(message = "version不能为空") String version, @RequestParam  @NotNull(message = "systemType不能为空") Integer systemType,HttpServletRequest request) {
    	  //boolean flag = this.upgradeService.headList(request);
    	boolean flag = true;
    	    if (flag) {
    	      return this.upgradeService.browserUpgrade(version, systemType);
    	    }
    	    return null;
    }

    @PostMapping("plugin")
    @ApiOperation("控件升级接口")
    public R pluginUpgrade(@RequestBody UpgradeRequest request,HttpServletRequest request1) {
    	 boolean flag = this.upgradeService.headList(request1);
    	    if (flag) {
    	      return this.upgradeService.pluginUpgrade(request);
    	    }
    	    return null;
    }

    @PostMapping("cert")
    @ApiOperation("证书升级接口")
    public R certUpgrade(@RequestBody UpgradeRequest request,HttpServletRequest request1) {
    	 boolean flag = this.upgradeService.headList(request1);
    	    if (flag) {
    	      return this.upgradeService.certUpgrade(request);
    	    }
    	    return null;
    }
}
