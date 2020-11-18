package com.ht.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CountRateLimiterUtil {
    private static Logger logger = LoggerFactory.getLogger(CountRateLimiterUtil.class);
    /**
     * 允许的接口调用并发数量，初始值10
     */
    private Integer concurrenceAmount = 10;
    /**
     * 原子性操作计数对象
     */
    private AtomicInteger count = new AtomicInteger(0);

    public Boolean countJudge() {
        // // 并发计数+1，并判断当前接口并发数量是否小于等于设定值
        if (count.incrementAndGet() <= concurrenceAmount) {
            return true;
        }
        // 未通过计数需再-1
        count.decrementAndGet();
        return false;
    }

    public void countSubtract() {
        // 并发计数-1
        count.decrementAndGet();
    }

    public Integer getConcurrenceAmount() {
        return concurrenceAmount;
    }

    public void setConcurrenceAmount(Integer concurrenceAmount) {
        this.concurrenceAmount = concurrenceAmount;
    }

    public Integer getAtomicIntegerCount() {
        return count.get();
    }
}
