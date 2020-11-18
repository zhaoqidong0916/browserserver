package com.ht.common.exceptions.exception;

/**
 * @author: yang.yang
 * @email: Mryang905032390@163.com
 * @Date: 2020/9/16 14:04
 **/
public class CountRateLimiterException extends RuntimeException {
    public CountRateLimiterException() {
        super("系统繁忙请稍后再试");
    }

    public CountRateLimiterException(String message) {
        super(message);
    }

    public CountRateLimiterException(Throwable cause) {
        super(cause);
    }

    public CountRateLimiterException(String message, Throwable cause) {
        super(message, cause);
    }
}
