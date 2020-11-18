package com.ht.common.controller.back.type;

import com.ht.common.page.R;
import com.ht.common.request.upgrade.cert.type.CertTypeAddRequest;
import com.ht.common.request.upgrade.cert.type.CertTypePageReqeust;
import com.ht.common.request.upgrade.cert.type.CertTypeUpdateRequest;
import com.ht.common.service.IBrowserCertTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-23 09:11
 **/
@RestController
@RequestMapping("type")
@Api(tags = "类型管理--证书类型管理")
public class BrowserCertTypeController {

    @Autowired
    private IBrowserCertTypeService browserCertTypeService;

    
    @PostMapping("cert/add")
    @ApiOperation(value = "添加证书类型")
    public R certTypeAdd(@Valid @RequestBody CertTypeAddRequest request) {
        return browserCertTypeService.certTypeAdd(request);
    }

    @PutMapping("cert/update")
    @ApiOperation(value = "修改证书类型")
    public R certTypeUpdate(@Valid @RequestBody CertTypeUpdateRequest request) {
        return browserCertTypeService.certTypeUpdate(request);
    }

    @PostMapping("cert/list")
    @ApiOperation(value = "列表")
    public R certTypeList(@RequestBody CertTypePageReqeust request) {
        return browserCertTypeService.certTypeList(request);
    }

    @PostMapping("cert/delete/{id}")
    @ApiOperation(value = "删除证书类型")
    public R certTypeDelete(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserCertTypeService.certTypeDelete(id);
    }

    @GetMapping("cert/detail/{id}")
    @ApiOperation(value = "详情")
    public R certTypeDetail(@NotBlank(message = "id不能为空") @PathVariable String id) {
        return browserCertTypeService.certTypeDetail(id);
    }

}
