package com.ht.common.controller.back.application;

import com.ht.common.page.R;
import com.ht.common.request.application.resource.ApplicationResourceAddRequest;
import com.ht.common.request.application.resource.ApplicationResourcePageRequest;
import com.ht.common.request.application.resource.ApplicationResourceUpdateRequest;
import com.ht.common.service.IAuthUserService;
import com.ht.common.service.IBrowserApplicationResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.ServletRequest;
import javax.validation.Valid;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"application"})
@Validated
@Api(tags={"应用管理--背景视频图片文字管理"})
public class BrowserApplicationResourcesController
{
  @Autowired
  private IBrowserApplicationResourcesService browserApplicationResourcesService;
  @Autowired
  private IAuthUserService authUserService;
  
  @PostMapping({"resource/add"})
  @ApiOperation("添加背景配置")
  public R resourceAdd(@Valid @RequestBody ApplicationResourceAddRequest request)
  {
    return this.browserApplicationResourcesService.resourceAdd(request);
  }
  
  @PostMapping({"resource/update"})
  @ApiOperation("修改背景信息")
  public R resourceUpdate(@Valid @RequestBody ApplicationResourceUpdateRequest request)
  {
    return this.browserApplicationResourcesService.resourceUpdate(request);
  }
  
  @PostMapping({"resource/list"})
  @ApiOperation("列表")
  public R resourceList(@RequestBody ApplicationResourcePageRequest request, ServletRequest request1)
  {
    boolean isFlag = this.authUserService.autoFlag(request1);
    if (isFlag) {
      return this.browserApplicationResourcesService.resourceList(request);
    }
    return null;
  }
  
  @PostMapping({"resource/delete/{id}"})
  @ApiOperation("删除背景配置")
  public R resourceDelete(@NotBlank(message="id不能为空") @PathVariable String id)
  {
    return this.browserApplicationResourcesService.resourceDelete(id);
  }
  
  @GetMapping({"resource/detail/{id}"})
  @ApiOperation("详情")
  public R resourceDetail(@NotBlank(message="id不能为空") @PathVariable String id)
  {
    return this.browserApplicationResourcesService.resourceDetail(id);
  }
  
  @PostMapping({"resource/upload"})
  @ApiOperation("上传")
  public R resourceUpload(@RequestParam("file") MultipartFile file)
  {
    return this.browserApplicationResourcesService.resourceUpload(file);
  }
}
