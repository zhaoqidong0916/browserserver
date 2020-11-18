package com.ht.common.controller.back.application;


import com.ht.common.page.R;
import com.ht.common.request.application.url.ApplicationUrlAddRequest;
import com.ht.common.request.application.url.ApplicationUrlPageRequest;
import com.ht.common.request.application.url.ApplicationUrlUpdateRequest;
import com.ht.common.service.IAuthUserService;
import com.ht.common.service.IBrowserApplicationIconsService;
import com.ht.common.service.IBrowserApplicationUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

/**
 * 浏览器首页
 *
 * @author yakun.shi
 * @since 2020-03-11
 */
@RestController
@RequestMapping("application")
@Api(tags = "应用管理--浏览器页面展示九宫格")
public class BrowserApplicationUrlController {

    @Autowired
    private IBrowserApplicationUrlService applicationUrlService;
    @Autowired
    private IAuthUserService authUserService;
    @Autowired
    private IBrowserApplicationIconsService browserApplicationIconsService;
    
    @PostMapping("url/add")
    @ApiOperation(value = "添加首页url配置")
    public R urlAdd(@Valid @RequestBody ApplicationUrlAddRequest request) {
        return applicationUrlService.urlAdd(request);
    }

    @PostMapping("url/update")
    @ApiOperation(value = "修改首页url信息")
    public R urlUpdate(@Valid @RequestBody ApplicationUrlUpdateRequest request) {
        return applicationUrlService.urlUpdate(request);
    }

    @PostMapping("url/list")
    @ApiOperation(value = "列表")
    public R urlList(@RequestBody ApplicationUrlPageRequest request,ServletRequest request1) {
    	boolean isFlag = this.authUserService.autoFlag(request1);
        if (isFlag) {
          return this.applicationUrlService.urlList(request);
        }
        return null;
    }

    @PostMapping("url/delete/{id}")
    @ApiOperation(value = "删除首页url配置")
    public R urlDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return applicationUrlService.urlDelete(id);
    }

    @GetMapping("url/detail/{id}")
    @ApiOperation(value = "详情")
    public R urlDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return applicationUrlService.urlDetail(id);
    }



    @PostMapping("icon/upload")
    @ApiOperation(value = "上传")
    public R iconUpload(@RequestParam("file") MultipartFile[] file) {
        return browserApplicationIconsService.iconUpload(file);
    }

    @GetMapping("icon/list")
    @ApiOperation(value = "列表")
    public R iconList() {
        return browserApplicationIconsService.iconList();
    }

    @PostMapping("icon/delete/{id}")
    @ApiOperation(value = "删除首页url配置")
    public R iconDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserApplicationIconsService.iconDelete(id);
    }

}

