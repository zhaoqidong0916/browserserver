package com.ht.common.controller.back.homepage;

import com.ht.common.page.R;
import com.ht.common.service.IHomePageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 首页图形展示
 * @author: yaKun.shi
 * @create: 2020-04-27 09:36
 **/
@RestController("homePage")
@Api(tags = "首页展示")
public class HomePageController {

    @Autowired
    private IHomePageService homePageService;

    @GetMapping("count/{type}")
    @ApiOperation("获取统计数量")
    public R getCollectCount(@PathVariable Integer type) {
        return homePageService.getCollectCount(type);
    }

    @GetMapping("browser/{type}")
    @ApiOperation("获取客户端活跃度折线图")
    public R getBrowserActiveCount(@PathVariable Integer type) {
        return homePageService.getBrowserActiveCount(type);
    }

    @GetMapping("browserUrlCount/{type}")
    @ApiOperation("获取客户端首页菜单")
    public R getBrowserUrlCount(@PathVariable Integer type) {
        return homePageService.getBrowserUrlCount(type);
    }
}
