package com.ht.common.config.logaop.operatelogaop;


import java.lang.annotation.*;

/**
 * @Author yakun.shi
 * @Description //日志搜索注解
 * @Date 2019/6/26 8:53
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLogger {
    //日志具体内容
    String value();

    //日志类型
    OperateType type();

}
