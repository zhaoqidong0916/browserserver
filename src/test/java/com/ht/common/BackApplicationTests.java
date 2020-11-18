package com.ht.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.common.dao.BrowserLogMapper;
import com.ht.common.entity.BrowserLog;
import com.ht.common.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackApplicationTests {

    @Autowired
    private BrowserLogMapper browserLogMapper;

    @Test
    public void contextLoads() {
//        List<BrowserLog> browserLogs = browserLogMapper.selectList(new QueryWrapper<>());
//        for (int i = 0; i <browserLogs.size() ; i++) {
//            BrowserLog browserLog = browserLogs.get(i);
//            Date createTime = browserLog.getCreateTime();
//            LocalDateTime localDateTime = DateUtils.convertDateToLocalDateTime(createTime);
//            int day = localDateTime.getDayOfMonth();
//            int year = localDateTime.getYear();
//            int month = localDateTime.getMonthValue();
//            BrowserLog browserLog1 = new BrowserLog();
//            browserLog1.setDay(day);
//            browserLog1.setYear(year);
//            browserLog1.setMonth(month);
//            browserLog1.setId(browserLog.getId());
//            browserLogMapper.updateById(browserLog1);
//        }

    }

}
