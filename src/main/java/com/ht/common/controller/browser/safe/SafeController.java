package com.ht.common.controller.browser.safe;

import com.ht.common.page.R;
import com.ht.common.request.log.browserlog.ActionLogCollectRequest;
import com.ht.common.request.log.browserlog.InfoCollectRequest;
import com.ht.common.service.ISafeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 浏览器安全访问策略
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
@RestController
@RequestMapping("browser")
@Validated
@Api(tags = "浏览器服务接口--浏览器安全策略接口")
public class SafeController {
    
    @Autowired 
    private ISafeService browserService;


    @GetMapping("safeList")
    @ApiOperation("安全管理接口数据")
    public R safeList(@RequestParam @Valid @NotEmpty(message = "md5不能为空") String md5,HttpServletRequest request){
    	 boolean flag = this.browserService.headList(request);
    	    if (flag) {
    	      return this.browserService.safeList(md5);
    	    }
    	    return null;
    }


    @GetMapping("configList")
    @ApiOperation("策略管理接口数据")
    public R configList(@RequestParam @Valid @NotEmpty(message = "md5不能为空") String md5,HttpServletRequest request){
    	//boolean flag = this.browserService.headList(request);
    	boolean flag = true;
        if (flag) {
          return this.browserService.configList(md5);
        }
        return null;
    }


    @PostMapping("infoCollect")
    @ApiOperation("行为统计接口")
    public R infoCollect(@RequestBody InfoCollectRequest request,HttpServletRequest request1){
    	 boolean flag = this.browserService.headList(request1);
    	    if (flag) {
    	      return this.browserService.infoCollect(request);
    	    }
    	    return null;	
    }

    /**
     * 新浏览器行为统计接口
     * 与上面的行为统计接收参数不同
     */
    @PostMapping("actionLogCollect")
    @ApiOperation("新浏览器行为日志统计接口")
    public R actionLogCollectCollect(@RequestBody ActionLogCollectRequest request, HttpServletRequest request1){
        boolean flag = this.browserService.headList(request1);
        if (flag) {
            return this.browserService.actionLogCollect(request);
        }
        return null;
    }
}

