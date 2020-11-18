package com.ht.common.config.countratelimiteraop;

import java.lang.annotation.*;

/**
 * @author: yang.yang
 * @email: Mryang905032390@163.com
 * @Date: 2020/9/16 10:50
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CountRateLimiterAdd {
    // 接口类型
    UpgradeType type() default UpgradeType.all;
}
