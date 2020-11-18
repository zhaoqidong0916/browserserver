package com.ht.common.controller.back.system.user;

import com.ht.common.service.IAuthUserService;
import com.ht.common.page.R;
import com.ht.common.request.system.user.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 登录登出控制类
 * @author: yaKun.shi
 * @create: 2020-03-05 11:46
 **/
@RestController
@Validated
@Api(tags = "用户登录登出")
public class LoginController {

    @Autowired
    private IAuthUserService authUserService;

    @PostMapping("userLogin")
    @ApiOperation(value = "用户登录")
    public R login(@RequestBody LoginRequest request) {
        R r = authUserService.login(request);
        return r;
    }

    @GetMapping("logout")
    @ApiOperation(value = "退出登录")
    public R logout(HttpServletRequest request) {
        R r = authUserService.logout(request);
        return r;
    }
}
