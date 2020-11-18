package com.ht.common.controller.back.system.log;


import com.ht.common.service.ISystemLoginLogService;
import com.ht.common.service.ISystemOperateLogService;
import com.ht.common.page.R;
import com.ht.common.request.log.systemlog.SystemLogPageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
@RestController
@RequestMapping("log")
@Api(tags = "日志管理--系统日志管理")
public class SystemLogController {

    @Autowired
    private ISystemOperateLogService operateLogService;

    @Autowired
    private ISystemLoginLogService loginLogService;

    @PostMapping("loginLog")
    @ApiOperation(value = "登录日志")
    public R loginLogList(@RequestBody SystemLogPageRequest request){
        return loginLogService.loginLogList(request);
    }

    @PostMapping("operateLog")
    @ApiOperation(value = "操作日志")
    public R operateLogList(@RequestBody SystemLogPageRequest request){
        return operateLogService.operateLogList(request);
    }

}

