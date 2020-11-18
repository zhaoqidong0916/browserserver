package com.ht.common.controller.back.config;


import com.ht.common.page.R;
import com.ht.common.request.watermark.WatermarkPageRequest;
import com.ht.common.request.watermark.WatermarkUpdateRequest;
import com.ht.common.service.IAuthUserService;
import com.ht.common.service.IBrowserWatermarkMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

/**
 * 浏览器水印配置
 *
 * @author yakun.shi
 * @since 2020-03-06
 */
@RestController
@RequestMapping("config")
@Api(tags = {"策略管理--浏览器水印配置"})
public class BrowserWatermarkMessageController {

    @Autowired
    private IBrowserWatermarkMessageService browserWatermarkMessageService;
    @Autowired
    private IAuthUserService authUserService;

    @PostMapping("watermark/update")
    @ApiOperation(value = "修改水印信息")
    public R watermarkUpdate(@Valid @RequestBody WatermarkUpdateRequest request) {
        return browserWatermarkMessageService.watermarkUpdate(request);
    }

    @PostMapping("watermark/list")
    @ApiOperation(value = "列表")
    public R watermarkList(@RequestBody WatermarkPageRequest request,ServletRequest request1) {
    	 boolean isFlag = this.authUserService.autoFlag(request1);
    	    if (isFlag) {
    	      return this.browserWatermarkMessageService.watermarkList(request);
    	    }
    	    return null;
    }


    @GetMapping("watermark/detail/{id}")
    @ApiOperation(value = "详情")
    public R watermarkDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserWatermarkMessageService.watermarkDetail(id);
    }

    @PostMapping("watermark/upload")
    @ApiOperation(value = "上传")
    public R watermarkUpload(@RequestParam("file") MultipartFile file) {
        return browserWatermarkMessageService.watermarkUpload(file);
    }

}

