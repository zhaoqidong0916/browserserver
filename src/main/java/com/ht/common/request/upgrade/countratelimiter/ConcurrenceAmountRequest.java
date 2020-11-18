package com.ht.common.request.upgrade.countratelimiter;

import javax.validation.constraints.NotNull;

/**
 * @author: yang.yang
 * @email: Mryang905032390@163.com
 * @Date: 2020/9/18 17:05
 **/
public class ConcurrenceAmountRequest {
    /**
     * 限流限定值
     */
    @NotNull(message = "concurrenceAmount不能为空")
    private Integer concurrenceAmount;

    public Integer getConcurrenceAmount() {
        return concurrenceAmount;
    }

    public void setConcurrenceAmount(Integer concurrenceAmount) {
        this.concurrenceAmount = concurrenceAmount;
    }
}
