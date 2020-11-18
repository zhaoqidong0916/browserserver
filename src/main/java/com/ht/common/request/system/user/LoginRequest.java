package com.ht.common.request.system.user;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @program: browser_template
 * @description: 用户登录
 * @author: yaKun.shi
 * @create: 2019-09-23 11:52
 **/
public class LoginRequest {

    @NotEmpty(message = "登录名不能为空")
    private String loginName;
    @NotEmpty(message = "password不能为空")
    @Length(min = 6,max = 12,message = "密码格式不正确")
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
