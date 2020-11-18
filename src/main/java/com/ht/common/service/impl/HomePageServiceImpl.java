package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.common.controller.back.homepage.ChartResult;
import com.ht.common.controller.back.homepage.CountResult;
import com.ht.common.dao.BrowserApplicationUrlMapper;
import com.ht.common.dao.BrowserLogMapper;
import com.ht.common.dao.BrowserUpgradeHistoryMapper;
import com.ht.common.dao.SystemLoginLogMapper;
import com.ht.common.entity.BrowserApplicationUrl;
import com.ht.common.entity.BrowserLog;
import com.ht.common.entity.BrowserUpgradeHistory;
import com.ht.common.entity.SystemLoginLog;
import com.ht.common.page.R;
import com.ht.common.service.IHomePageService;
import com.ht.common.utils.DateUtils;
import com.ht.common.utils.IndexCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static com.ht.common.constant.Constant.*;
import static com.ht.common.enums.ResponseEnum.LACK_PARAMETERS;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-04-27 09:38
 **/
@Service
public class HomePageServiceImpl implements IHomePageService {

    @Autowired
    private BrowserUpgradeHistoryMapper browserUpgradeHistoryMapper;

    @Autowired
    private SystemLoginLogMapper systemLoginLogMapper;

    @Autowired
    private BrowserApplicationUrlMapper browserApplicationUrlMapper;

    @Autowired
    private BrowserLogMapper browserLogMapper;


    private static List<Integer> HOUR = new ArrayList<>();

    private static List<Integer> MONTH = new ArrayList<>();


    static {
        HOUR.add(8);
        HOUR.add(9);
        HOUR.add(10);
        HOUR.add(11);
        HOUR.add(12);
        HOUR.add(13);
        HOUR.add(14);
        HOUR.add(15);
        HOUR.add(16);
        HOUR.add(17);
        HOUR.add(18);
        HOUR.add(19);
        HOUR.add(20);


        MONTH.add(1);
        MONTH.add(2);
        MONTH.add(3);
        MONTH.add(4);
        MONTH.add(5);
        MONTH.add(6);
        MONTH.add(7);
        MONTH.add(8);
        MONTH.add(9);
        MONTH.add(10);
        MONTH.add(11);
        MONTH.add(12);

    }

    @Override
    public R getCollectCount(Integer type) {
        CountResult countResult = new CountResult();
        if (LOGIN_COUNT_TYPE.equals(type)) {
            String date = DateUtils.dateToString(LocalDateTime.now());
            String beginDate = date + " 00:00:00";
            String endDate = date + " 23:59:59";
            QueryWrapper<SystemLoginLog> wrapper = new QueryWrapper<>();
            wrapper.between("CREATE_TIME", beginDate, endDate);
            Integer count = systemLoginLogMapper.selectCount(wrapper);
            wrapper.orderByDesc("CREATE_TIME");
            Date recentlyTime = systemLoginLogMapper.selectRecentlyTime();
            countResult.setCount(count);
            countResult.setTime(recentlyTime);
        } else if (CERT_COUNT_TYPE.equals(type)) {
            countResult = getUpgradeCount(CERT_COUNT_TYPE);
        } else if (PLUGIN_COUNT_TYPE.equals(type)) {
            countResult = getUpgradeCount(PLUGIN_COUNT_TYPE);
        } else if (BROWSER_COUNT_TYPE.equals(type)) {
            countResult = getUpgradeCount(BROWSER_COUNT_TYPE);
        } else {
            return R.result(LACK_PARAMETERS);
        }
        return R.success(countResult);
    }

    @Override
    public R getBrowserActiveCount(Integer type) {
        if (BROWSER_DAY.equals(type)) {
            String stringHour = HOUR + "BROWSER_ACTIVE";
            if (IndexCache.get(stringHour) != null) {
                return R.success(IndexCache.get(stringHour));
            }
            String date = DateUtils.dateToString(LocalDateTime.now());
            String beginDate = date + " 08:00:00";
            String endDate = date + " 20:00:00";
            QueryWrapper<BrowserLog> wrapper = new QueryWrapper<>();
            wrapper.between("CREATE_TIME", beginDate, endDate);
            List<BrowserLog> browserLogs = browserLogMapper.selectList(wrapper);
            List<BrowserLog> list = new ArrayList<>();
            for (int i = 0; i < browserLogs.size(); i++) {
                BrowserLog browserLog = browserLogs.get(i);
                Date createTime = browserLog.getCreateTime();
                LocalDateTime localDateTime = DateUtils.convertDateToLocalDateTime(createTime);
                int hour = localDateTime.getHour();
                browserLog.setHour(hour);
                list.add(browserLog);
            }
            List<Integer> resultList = new ArrayList<>();
            Map<Integer, List<BrowserLog>> map = list.stream().collect(Collectors.groupingBy(BrowserLog::getHour));
            for (int i = 0; i < HOUR.size(); i++) {
                Integer key = HOUR.get(i);
                if (map.containsKey(key)) {
                    resultList.add(map.get(key).size());
                } else {
                    resultList.add(0);
                }
            }
            IndexCache.put(stringHour, resultList);
            return R.success(resultList);
        } else if (BROWSER_MONTH.equals(type)) {
            String browserMonth = BROWSER_MONTH + "BROWSER_ACTIVE";
            if (IndexCache.get(browserMonth) != null) {
                return R.success(IndexCache.get(browserMonth));
            }
            String stringNow = DateUtils.dateToString(LocalDateTime.now());
            LocalDateTime now = LocalDateTime.now();
            int dayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
            List<String> DAY = new ArrayList<>();
            List<String> xLine = new ArrayList<>();
            for (int i = dayOfMonth - 1; i >= 0; i--) {
                LocalDateTime localDateTime = now.minusDays(i);
                String date = DateUtils.dateToString2(localDateTime);
                xLine.add(date);
//                int day = now.minusDays(i).getDayOfMonth();
                DAY.add(date);
            }
            LocalDateTime localDateTime = now.minusDays(dayOfMonth);
            String before = DateUtils.dateToString(localDateTime);
            String beginDate = before + " 08:00:00";
            String endDate = stringNow + " 20:00:00";
            QueryWrapper<BrowserLog> wrapper = new QueryWrapper<>();
            wrapper.between("CREATE_TIME", beginDate, endDate);
            List<BrowserLog> browserLogs = browserLogMapper.selectList(wrapper);
            Map<String, List<BrowserLog>> map = browserLogs.stream().collect(Collectors.groupingBy(d -> fetchGroupKey(d)));
            List<Integer> yLine = new ArrayList<>();
            for (int i = 0; i < DAY.size(); i++) {
                String key = DAY.get(i);
                if (map.containsKey(key)) {
                    yLine.add(map.get(key).size());
                } else {
                    yLine.add(0);
                }
            }
            Map<String, Object> resultMap = new HashMap<>(4);
            resultMap.put("xLine", xLine);
            resultMap.put("yLine", yLine);
            IndexCache.put(browserMonth, resultMap);
            return R.success(resultMap);
        } else if (BROWSER_YEAR.equals(type)) {
            String browserYear = BROWSER_YEAR + "BROWSER_ACTIVE";
            if (IndexCache.get(browserYear) != null) {
                return R.success(IndexCache.get(browserYear));
            }
            LocalDateTime now = LocalDateTime.now();
            int year = now.getYear();
            QueryWrapper<BrowserLog> wrapper = new QueryWrapper<>();
            wrapper.eq("YEAR", year);
            List<BrowserLog> browserYearLogs = browserLogMapper.selectList(wrapper);
            List<Integer> resultList = new ArrayList<>();
            Map<Integer, List<BrowserLog>> map = browserYearLogs.stream().collect(Collectors.groupingBy(BrowserLog::getMonth));
            for (int i = 0; i < MONTH.size(); i++) {
                Integer key = MONTH.get(i);
                if (map.containsKey(key)) {
                    resultList.add(map.get(key).size());
                } else {
                    resultList.add(0);
                }
            }
            IndexCache.put(browserYear, resultList);
            return R.success(resultList);
        } else {
            return R.result(LACK_PARAMETERS);
        }
    }


    private String fetchGroupKey(BrowserLog detail) {
        int month = detail.getMonth();
        String stringMonth = month + "";
        if (month < 10) {
            stringMonth = "0" + month;
        }
        return stringMonth + "/" + detail.getDay();

    }


    @Override
    public R getBrowserUrlCount(Integer type) {
        if (HISTOGRAM_TYPE.equals(type)) {
            String key = HISTOGRAM_TYPE + "URL";
            if (IndexCache.get(key) != null) {
                return R.success(IndexCache.get(key));
            }
        }
        if (PIE_CHART_TYPE.equals(type)) {
            String key = PIE_CHART_TYPE + "URL";
            if (IndexCache.get(key) != null) {
                return R.success(IndexCache.get(key));
            }
        }
        List<BrowserApplicationUrl> browserApplicationUrls =
                browserApplicationUrlMapper.selectList(new QueryWrapper<>());
        String date = DateUtils.dateToString(LocalDateTime.now());
        String beginDate = date + " 00:00:00";
        String endDate = date + " 23:59:59";
        List<String> xLine = new ArrayList<>();
        List<Integer> yLine = new ArrayList<>();
        List<ChartResult> chartResults = new ArrayList<>();
        for (int i = 0; i < browserApplicationUrls.size(); i++) {
            BrowserApplicationUrl browserApplicationUrl = browserApplicationUrls.get(i);
            String urlAddress = browserApplicationUrl.getUrlAddress();
            String urlName = browserApplicationUrl.getUrlName();
            QueryWrapper<BrowserLog> wrapper = new QueryWrapper<>();
            wrapper.like("URL", urlAddress);
            wrapper.between("CREATE_TIME", beginDate, endDate);
            Integer count = browserLogMapper.selectCount(wrapper);
            xLine.add(urlName);
            yLine.add(count);
            ChartResult chartResult = new ChartResult();
            chartResult.setValue(count);
            chartResult.setUrl(urlAddress);
            chartResult.setName(urlName);
            chartResults.add(chartResult);
        }
        if (HISTOGRAM_TYPE.equals(type)) {
            Map<String, Object> map = new HashMap<>(4);
            map.put("xLine", xLine);
            map.put("yLine", yLine);
            IndexCache.put(HISTOGRAM_TYPE + "URL", map);
            return R.success(map);
        } else if (PIE_CHART_TYPE.equals(type)) {
            List<ChartResult> results = chartResults.stream().sorted((o1, o2) -> o2.getValue() - o1
                    .getValue()).collect(Collectors.toList());
            if (results.size() > 5) {
                results = results.subList(0, 5);
            }
            IndexCache.put(PIE_CHART_TYPE + "URL", results);
            return R.success(results);
        } else {
            return R.result(LACK_PARAMETERS);
        }
    }


    /**
     * 获取升级次数
     *
     * @param type
     * @return
     */
    private CountResult getUpgradeCount(Integer type) {
        CountResult countResult = new CountResult();
        QueryWrapper<BrowserUpgradeHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("TYPE", type);
        Integer count = browserUpgradeHistoryMapper.selectCount(wrapper);
        Date recentlyTime = browserUpgradeHistoryMapper.selectRecentlyTime(type);
        countResult.setCount(count);
        countResult.setTime(recentlyTime);
        return countResult;
    }
}
