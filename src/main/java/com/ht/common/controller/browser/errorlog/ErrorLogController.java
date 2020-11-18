package com.ht.common.controller.browser.errorlog;


import com.ht.common.controller.browser.safe.request.ErrorCollectRequest;
import com.ht.common.controller.browser.safe.request.ErrorJsCollectRequest;
import com.ht.common.page.R;

import com.ht.common.service.IBrowserErrorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 浏览器安全访问策略
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
@RestController
@RequestMapping("browserLog")
@Validated
@Api(tags = "接收浏览器上送错误相关日志")
public class ErrorLogController {
    
    @Autowired 
    private IBrowserErrorLogService browserErrorLogService;

    /**
     * 接收浏览器上送的 客户端错误 日志接口
     *
     * @param request
     * @return
     */
    @PostMapping("errorCollect")
    @ApiOperation("客户端错误统计接口")
    public R errorCollect(@RequestBody ErrorCollectRequest request){
        return browserErrorLogService.errorCollect(request);
    }

    /**
     * 接收浏览器上送的 js错误 日志接口
     *
     * @param request
     * @return
     */
    @PostMapping("errorJsCollect")
    @ApiOperation("JS错误统计接口")
    public R errorJsCollect(@RequestBody ErrorJsCollectRequest request){
        return browserErrorLogService.errorJsCollect(request);
    }
}

