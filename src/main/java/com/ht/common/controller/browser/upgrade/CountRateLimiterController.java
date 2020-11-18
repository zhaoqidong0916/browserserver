package com.ht.common.controller.browser.upgrade;

import com.ht.common.page.R;
import com.ht.common.request.upgrade.countratelimiter.ConcurrenceAmountRequest;
import com.ht.common.utils.CountRateLimiterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author: yang.yang
 * @email: Mryang905032390@163.com
 * @Date: 2020/9/17 8:53
 **/
@RestController
@RequestMapping("countRateLimiter")
@Validated
@Api(tags = "限流管理--流量管控")
public class CountRateLimiterController {
    private static Logger logger = LoggerFactory.getLogger(CountRateLimiterController.class);

    @Autowired
    private CountRateLimiterUtil countRateLimiterUtil;

    @PostMapping("concurrenceAmount/list")
    @ApiOperation("列表")
    public R getConcurrenceAmount() {
        Integer concurrenceAmount = countRateLimiterUtil.getConcurrenceAmount();
        logger.info("当前并发限数concurrenceAmount:" + concurrenceAmount);
        // 返给前端指定数据
        ArrayList<HashMap<String, Object>> responList = new ArrayList<>();
        HashMap<String, Object> responMap = new HashMap<>();
        responMap.put("concurrenceAmount", concurrenceAmount);
        responList.add(responMap);
        return R.success(responList);
    }

    @GetMapping("concurrenceAmount/detail")
    @ApiOperation("详情")
    public R detail() {
        Integer concurrenceAmount = countRateLimiterUtil.getConcurrenceAmount();
        logger.info("当前并发限数concurrenceAmount:" + concurrenceAmount);
        // 返给前端指定数据
        HashMap<String, Object> responMap = new HashMap<>();
        responMap.put("concurrenceAmount", concurrenceAmount);
        return R.success(responMap);
    }

    @PostMapping("concurrenceAmount/update")
    @ApiOperation("流量配置")
    public R update(@Valid @RequestBody ConcurrenceAmountRequest request) {
        logger.info("更改【前】并发限数concurrenceAmount:" + countRateLimiterUtil.getConcurrenceAmount());
        countRateLimiterUtil.setConcurrenceAmount(request.getConcurrenceAmount());
        logger.info("更改【后】并发限数concurrenceAmount:" + countRateLimiterUtil.getConcurrenceAmount());
        return R.success();
    }
}