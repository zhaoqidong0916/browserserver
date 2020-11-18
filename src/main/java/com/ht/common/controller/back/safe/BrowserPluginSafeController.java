package com.ht.common.controller.back.safe;


import com.ht.common.page.R;
import com.ht.common.request.safe.plugin.PluginAddRequest;
import com.ht.common.request.safe.plugin.PluginPageRequest;
import com.ht.common.request.safe.plugin.PluginStateEditRequest;
import com.ht.common.request.safe.plugin.PluginUpdateRequest;
import com.ht.common.service.IAuthUserService;
import com.ht.common.service.IBrowserPluginSafeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
@RestController
@RequestMapping("safe")
@Validated
@Api(tags = "安全管理--插件管控")
public class BrowserPluginSafeController {

    @Autowired
    private IBrowserPluginSafeService browserPluginSafeService;
    @Autowired
    private IAuthUserService authUserService;
    @PostMapping("plugin/add")
    @ApiOperation(value = "添加插件管控")
    public R pluginAdd(@Valid @RequestBody PluginAddRequest request) {
        return browserPluginSafeService.pluginAdd(request);
    }

    @PostMapping("plugin/update")
    @ApiOperation(value = "修改插件管控")
    public R pluginUpdate(@Valid @RequestBody PluginUpdateRequest request) {
        return browserPluginSafeService.pluginUpdate(request);
    }


    @PostMapping("plugin/state/edit")
    @ApiOperation(value = "编辑插件黑白名单")
    public R pluginStateEdit(@Valid @RequestBody PluginStateEditRequest request) {
        return browserPluginSafeService.pluginStateEdit(request);
    }



    @PostMapping("plugin/list")
    @ApiOperation(value = "列表")
    public R pluginList(@RequestBody PluginPageRequest request,ServletRequest request1) {
    	  boolean isFlag = this.authUserService.autoFlag(request1);
    	    if (isFlag) {
    	      return this.browserPluginSafeService.pluginList(request);
    	    }
    	    return null;
    }

    @PostMapping("plugin/delete/{id}")
    @ApiOperation(value = "删除插件管控")
    public R pluginDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserPluginSafeService.pluginDelete(id);
    }

    @GetMapping("plugin/detail/{id}")
    @ApiOperation(value = "详情")
    public R pluginDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserPluginSafeService.pluginDetail(id);
    }
}

