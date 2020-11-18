package com.ht.common.config.countratelimiteraop;

import com.ht.common.exceptions.exception.CountRateLimiterException;
import com.ht.common.utils.CountRateLimiterUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: yang.yang
 * @email: Mryang905032390@163.com
 * @Date: 2020/9/16 11:13
 **/
@Aspect
@Component
public class CountRateLimiterAop {
    private static Logger logger = LoggerFactory.getLogger(CountRateLimiterAop.class);

    @Autowired
    private CountRateLimiterUtil countRateLimiterUtil;

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.ht.common.config.countratelimiteraop.CountRateLimiterAdd)")
    public void upgradePointcut() {
    }

    /**
     * 访问接口并发计数，判断限流及计数+
     */
    @Around("upgradePointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("访问接口并发计数，判断限流及计数+");
        // 判断接口是否达到指定并发数
        if (!countRateLimiterUtil.countJudge()) {
            throw new CountRateLimiterException();
        }
        logger.info("当前并发量：" + countRateLimiterUtil.getAtomicIntegerCount());
        return pjp.proceed();
    }

    /**
     * 访问接口 正常返回值 并发计数，计数-
     */
    @AfterReturning("upgradePointcut()")
    public void doAfterReturning() {
        // 获取当前并发数量
        if (countRateLimiterUtil.getAtomicIntegerCount() > 0) {
            countRateLimiterUtil.countSubtract();
        }
        logger.info("访问接口 正常返回值 并发计数，计数-，当前并发量：" + countRateLimiterUtil.getAtomicIntegerCount());
    }

    /**
     * 访问接口 异常 并发计数，计数-
     */
    @AfterThrowing(throwing = "ex", value = "upgradePointcut()")
    public void doAfterThrowing(Throwable ex) {
        // 异常处理，系统繁忙异常未做并发计数，所以不用计数-
        if (!"系统繁忙请稍后再试".equals(ex.getMessage())) {
            if (countRateLimiterUtil.getAtomicIntegerCount() > 0) {
                countRateLimiterUtil.countSubtract();
            }
            logger.info("访问接口 异常 并发计数，计数-，当前并发量：" + countRateLimiterUtil.getAtomicIntegerCount());
        }
    }
}
