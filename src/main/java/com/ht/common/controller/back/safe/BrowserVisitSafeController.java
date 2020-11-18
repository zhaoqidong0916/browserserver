package com.ht.common.controller.back.safe;


import com.ht.common.page.R;
import com.ht.common.request.safe.visit.VisitAddRequest;
import com.ht.common.request.safe.visit.VisitPageRequest;
import com.ht.common.request.safe.visit.VisitStateEditRequest;
import com.ht.common.request.safe.visit.VisitUpdateRequest;
import com.ht.common.service.IAuthUserService;
import com.ht.common.service.IBrowserVisitSafeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

/**
 * 浏览器安全访问策略
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
@RestController
@RequestMapping("safe")
@Validated
@Api(tags = "安全管理--访问管控")
public class BrowserVisitSafeController {
    
    @Autowired 
    private IBrowserVisitSafeService browserVisitSafeService;
    @Autowired
    private IAuthUserService authUserService;
    @PostMapping("visit/add")
    @ApiOperation(value = "添加访问名单")
    public R visitAdd(@Valid @RequestBody VisitAddRequest request) {
        return browserVisitSafeService.visitAdd(request);
    }

    @PostMapping("visit/update")
    @ApiOperation(value = "修改访问名单")
    public R visitUpdate(@Valid @RequestBody VisitUpdateRequest request) {
        return browserVisitSafeService.visitUpdate(request);
    }

    @PostMapping("visit/state/edit")
    @ApiOperation(value = "编辑访问黑白名单")
    public R visitStateEdit(@Valid @RequestBody VisitStateEditRequest request) {
        return browserVisitSafeService.visitStateEdit(request);
    }

    @PostMapping("visit/list")
    @ApiOperation(value = "列表")
    public R visitList(@RequestBody VisitPageRequest request,ServletRequest request1) {
    	 boolean isFlag = this.authUserService.autoFlag(request1);
    	    if (isFlag) {
    	      return this.browserVisitSafeService.visitList(request);
    	    }
    	    return null;
    }

    @PostMapping("visit/delete/{id}")
    @ApiOperation(value = "删除访问黑名单")
    public R visitDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserVisitSafeService.visitDelete(id);
    }

    @GetMapping("visit/detail/{id}")
    @ApiOperation(value = "详情")
    public R visitDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserVisitSafeService.visitDetail(id);
    }

}

