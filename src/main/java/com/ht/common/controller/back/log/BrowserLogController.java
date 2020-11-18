package com.ht.common.controller.back.log;


import com.ht.common.service.IBrowserLogService;
import com.ht.common.page.R;
import com.ht.common.request.log.browserlog.BrowserLogPageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 浏览器行为日志
 *
 * @author yakun.shi
 * @since 2020-03-10
 */
@RestController
@RequestMapping("log")
@Api(tags = "日志管理--浏览器行为日志")
public class BrowserLogController {

    @Autowired
    private IBrowserLogService browserLogService;

    /**
     * 行为日志 一级目录
     * @param request
     * @return
     */
    @PostMapping("browserLog")
    @ApiOperation(value = "一级目录")
    public R systemLogList(@RequestBody BrowserLogPageRequest request){
        return browserLogService.systemLogList(request);
    }

    /**
     * 行为日志 二级目录
     * @param request
     * @return
     */
    @PostMapping("browserLog2")
    @ApiOperation(value = "二级目录")
    public R systemLog2List(@RequestBody BrowserLogPageRequest request){
        return browserLogService.systemLog2List(request);
    }

    /**
     * 新浏览器行为日志统计接口 一级目录
     * @param request
     * @return
     */
    @PostMapping("browserActionLog")
    @ApiOperation(value = "新浏览器行为日志统计 一级接口")
    public R browserActionLogList(@RequestBody BrowserLogPageRequest request){
        return browserLogService.browserActionLogList(request);
    }

    /**
     * 新浏览器行为日志统计接口 二级目录
     * @param request
     * @return
     */
    @PostMapping("browserActionLog2")
    @ApiOperation(value = "新浏览器行为日志统计 二级接口")
    public R browserActionLog2List(@RequestBody BrowserLogPageRequest request){
        return browserLogService.browserActionLog2List(request);
    }
}

