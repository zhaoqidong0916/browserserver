package com.ht.common.controller.browser.application;

import com.ht.common.page.R;
import com.ht.common.service.IApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-11 15:25
 **/
@RestController
@RequestMapping("application")
@Api(tags = "浏览器首页js调用服务接口")
public class ApplicationController {

    @Autowired
    private IApplicationService applicationService;

    @GetMapping("resourceList")
    @ApiOperation("背景图消息接口数据")
    public R resourceList(HttpServletRequest request){
    	 //boolean flag = this.applicationService.headList(request);
    	 boolean flag = true;
    	    if (flag) {
    	      return this.applicationService.resourceList();
    	    }
    	    return null;
    }


    @GetMapping("urlList")
    @ApiOperation("首页链接接口数据")
    public R urlList(HttpServletRequest request){
//    	 boolean flag = this.applicationService.headList(request);
//    	    if (flag) {
    	      return this.applicationService.urlList();
//    	    }
//    	    return null;
    }
}
