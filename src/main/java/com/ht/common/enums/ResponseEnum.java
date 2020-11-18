package com.ht.common.enums;

/**
 * @author yakun.shi
 * @date 2020/1/9 15:39
 * @desc
 */
public enum ResponseEnum {


    UNREGISTERED(1, "用户不存在或用户名错误！"),
    WRONG_PASSWORD(2, "密码错误！"),
    LACK_PARAMETERS(3, "缺少必传参数！"),
    PARAMETERS_ERROR(4, "json参数格式异常！"),
    OLD_PASSWORD_ERROR(5, "旧密码错误！"),
    PASSWORD_NOT_EQUAL(6, "两次密码不一致！"),
    DATA_EXIST(7, "数据已经存在！"),
    USERNAME_PASSWORD_NULL(8, "用户名或密码为空！"),
    USER_EXIST(9, "用户已经存在！"),
    IS_SUPERADMIN(10, "用户为超级管理员,不能被删除或者编辑！"),
    ROLE_HAS_USER(11, "此角色已被用户使用！"),
    ROLE_ISSUPERADMIN(12, "角色为内置角色,不能被删除！"),
    ROLE_ERROR(13, "用户角色被删除,被禁用或者未绑定,请联系管理员！"),
    UN_SUPPORT_FORMAT(14, "文件格式错误！"),
    UPLOAD_ERR(15, "上传出现错误！"),
    CACHE_NOT_UPDATE(16, "数据没有更新，不需要任何操作！"),
    VERSION_EXIST(17, "此版本安装包已经存在！"),
    PLUGIN_TYPE_USED(18, "插件类型已被使用不能修改！"),
    CERT_TYPE_USED(19, "证书类型已被使用不能修改！"),
    IS_UPDATE(20, "此数据已存在,是否要更新！"),
    RESOURCE_NAME_REPEAT(21, "资源名,名称,父类名称已经存在！"),
    ROLE_EXIST(22, "角色名已经存在！"),
    ROLE_IS_FORBBIN(22, "角色已被禁用,不能被操作！"),
    TIME_OUT(23, "上传文件错误！"),
    SUCCESS(200, "成功！"),
    USER_NOT_LOGIN(300, "用户未登录！"),
    USER_NO_AUTHORITY(403, "用户没有权限！"),
    SYSTEM_BUSY(501, "系统繁忙，请稍后再试！"),
    ERROR(500, "访问异常！");


    private Integer code;

    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public ResponseEnum setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
