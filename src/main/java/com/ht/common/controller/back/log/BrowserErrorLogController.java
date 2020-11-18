package com.ht.common.controller.back.log;


import com.ht.common.page.R;
import com.ht.common.request.log.browserlog.BrowserLogPageRequest;
import com.ht.common.service.IBrowserErrorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("log")
@Api(tags = "日志管理")
public class BrowserErrorLogController {

    @Autowired
    private IBrowserErrorLogService browserErrorLogService;

    /**
     * 错误日志
     * 查询客户端错误日志
     *
     * @param request
     * @return
     */
    @PostMapping("errorLog")
    @ApiOperation(value = "查询客户端错误日志")
    public R browserErrorLogList(@RequestBody BrowserLogPageRequest request) throws ParseException {
        return browserErrorLogService.browserErrorLogList(request);
    }

    /**
     * 查询JS错误日志列表
     */
    @PostMapping("browserJsErrorLogList")
    @ApiOperation(value = "查询JS错误日志列表")
    public R browserJsErrorLogList(@RequestBody BrowserLogPageRequest request) throws ParseException {
        return browserErrorLogService.browserJsErrorLogList(request);
    }
}