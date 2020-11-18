package com.ht.common.controller.back.upgrade;

import com.ht.common.page.R;
import com.ht.common.request.upgrade.cert.upgrade.CertUpgradeAddRequest;
import com.ht.common.request.upgrade.cert.upgrade.CertUpgradePageRequest;
import com.ht.common.request.upgrade.cert.upgrade.CertUpgradeUpdateRequest;
import com.ht.common.service.IAuthUserService;
import com.ht.common.service.IBrowserCertUpgradeService;
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
 * @description: 证书升级
 * @author: yaKun.shi
 * @create: 2020-03-23 09:05
 **/

@RestController
@RequestMapping("upgrade")
@Api(tags = "升级功能--证书升级管理")
public class BrowserCertUpgradeController {

    @Autowired
    private IBrowserCertUpgradeService browserCertUpgradeService;
    @Autowired
    private IAuthUserService authUserService;
    @PostMapping("cert/add")
    @ApiOperation(value = "添加证书安装包")
    public R certAdd(@Valid @RequestBody CertUpgradeAddRequest request) {
        return browserCertUpgradeService.certAdd(request);
    }

    @PostMapping("cert/add/update")
    @ApiOperation(value = "添加证书安装包,相同证书类型更新")
    public R certAddUpdate(@Valid @RequestBody CertUpgradeAddRequest request) {
        return browserCertUpgradeService.certAddUpdate(request);
    }

    @PostMapping("cert/update")
    @ApiOperation(value = "修改证书安装包")
    public R certUpdate(@Valid @RequestBody CertUpgradeUpdateRequest request) {
        return browserCertUpgradeService.certUpdate(request);
    }

    @PostMapping("cert/list")
    @ApiOperation(value = "列表")
    public R certList(@RequestBody CertUpgradePageRequest request,ServletRequest request1) {
    	 boolean isFlag = this.authUserService.autoFlag(request1);
    	    if (isFlag) {
    	      return this.browserCertUpgradeService.certList(request);
    	    }
    	    return null;
    }

    @PostMapping("cert/delete/{id}")
    @ApiOperation(value = "删除证书安装包")
    public R certDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserCertUpgradeService.certDelete(id);
    }

    @GetMapping("cert/detail/{id}")
    @ApiOperation(value = "详情")
    public R certDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserCertUpgradeService.certDetail(id);
    }

    @PostMapping("cert/upload")
    @ApiOperation(value = "上传")
    public R certUpload(@RequestParam("file") MultipartFile file) {
        return browserCertUpgradeService.certUpload(file);
    }

    @GetMapping("certType/list")
    @ApiOperation(value = "证书升级--证书类型列表")
    public R certTypeListForUpgrade(ServletRequest request) {
    	  boolean isFlag = this.authUserService.autoFlag(request);
    	    if (isFlag) {
    	      return this.browserCertUpgradeService.certTypeListForUpgrade();
    	    }
    	    return null;
    }
}
