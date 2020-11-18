package com.ht.common.controller.back.type;

import com.ht.common.page.R;
import com.ht.common.request.upgrade.plugintype.PluginTypeAddRequest;
import com.ht.common.request.upgrade.plugintype.PluginTypePageRequest;
import com.ht.common.request.upgrade.plugintype.PluginTypeUpdateRequest;
import com.ht.common.service.IBrowserPluginTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description: 控件类型
 * @author: yaKun.shi
 * @create: 2020-03-16 11:26
 **/
@RestController
@RequestMapping("type")
@Api(tags = "类型管理--控件类型管理")
@Validated
public class BrowserPluginTypeController {

    @Autowired
    private IBrowserPluginTypeService browserPluginTypeService;


    @PostMapping("plugin/add")
    @ApiOperation(value = "添加控件类型")
    public R pluginTypeAdd(@Valid @RequestBody PluginTypeAddRequest request) {
        return browserPluginTypeService.pluginTypeAdd(request);
    }

    @PutMapping("plugin/update")
    @ApiOperation(value = "修改控件类型")
    public R pluginTypeUpdate(@Valid @RequestBody PluginTypeUpdateRequest request) {
        return browserPluginTypeService.pluginTypeUpdate(request);
    }

    @PostMapping("plugin/list")
    @ApiOperation(value = "列表")
    public R pluginTypeList(@RequestBody PluginTypePageRequest request) {
        return browserPluginTypeService.pluginTypeList(request);
    }

    @PostMapping("plugin/delete/{id}")
    @ApiOperation(value = "删除控件类型")
    public R pluginTypeDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserPluginTypeService.pluginTypeDelete(id);
    }

    @GetMapping("plugin/detail/{id}")
    @ApiOperation(value = "详情")
    public R pluginTypeDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserPluginTypeService.pluginTypeDetail(id);
    }

}
