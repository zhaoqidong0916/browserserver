package com.ht.common.request.system.user;



import com.ht.common.page.PageRequest;

import java.io.Serializable;


/**
 * @ClassName UserPageRequest
 * @Author yakun.shi
 * @Date 2019/6/14 16:18
 * @Version 1.0
 **/
public class UserPageRequest extends PageRequest implements Serializable {

    private String username;

    private String loginName;

    private String createTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
