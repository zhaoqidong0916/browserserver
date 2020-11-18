package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.dao.BrowserActionLogMapper;
import com.ht.common.dao.BrowserLogMapper;
import com.ht.common.entity.BrowserActionLog;
import com.ht.common.entity.BrowserLog;
import com.ht.common.page.PageResponse;
import com.ht.common.page.R;
import com.ht.common.request.log.browserlog.BrowserLogPageRequest;
import com.ht.common.service.IBrowserLogService;
import com.ht.common.utils.DateUtils;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ht.common.enums.ResponseEnum.LACK_PARAMETERS;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-10
 */
@Service
public class BrowserLogServiceImpl extends ServiceImpl<BrowserLogMapper, BrowserLog> implements IBrowserLogService {

    @Autowired
    private BrowserLogMapper browserLogMapper;
    @Autowired
    private BrowserActionLogMapper browserActionLogMapper;

    @Override
    public R systemLogList(BrowserLogPageRequest request) {
        List<Map<String, Object>> logList = browserLogMapper.systemLogList(request);
        List<Map<String, Object>> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(logList)) {
            for (int i = 0; i < logList.size(); i++) {
                Map<String, Object> map = logList.get(i);
                Object o = map.get("MAC");
                if (o == null) {
                    o = "0";
                }
                String mac = o.toString();
                Date date = browserLogMapper.selectMaxTimeByMac(mac);
                Integer time1 = (int) date.getTime();
                String time = DateUtils.dateToString(date);
                map.put("createTime", time);
                map.put("sort", time1);
                map.put("mac",o);
                result.add(map);
            }
        } else {
            result = logList;
        }
        //根据时间排序
        List<Map<String, Object>> collect = result.stream().sorted((a, b) -> (int) b.get("sort") - (int) a.get("sort")).collect(Collectors.toList());
        Integer count = browserLogMapper.systemLogListCount(request);
        PageResponse response = PageResponse.createResponse(count, collect, request);
        return R.success(response);
    }

    @Override
    public R systemLog2List(BrowserLogPageRequest request) {
        if (StringUtils.isEmpty(request.getMac())) {
            return R.result(LACK_PARAMETERS, "mac参数不能为空");
        }
        Page<BrowserLog> browserLogPage = new Page<>();
        browserLogPage.setSize(request.getOffset());
        browserLogPage.setCurrent(request.getCurrent());
        QueryWrapper<BrowserLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MAC", request.getMac());
        if (StringUtils.isNotEmpty(request.getBrowserVersion())) {
            wrapper.likeLeft("BROWSER_VERSION", request.getBrowserVersion());
        }
        if (StringUtils.isNotEmpty(request.getIpAddress())) {
            wrapper.likeLeft("IP_ADDRESS", request.getIpAddress());
        }
        if (StringUtils.isNotEmpty(request.getTitle())) {
            wrapper.likeLeft("TITLE", request.getTitle());
        }
        if (StringUtils.isNotEmpty(request.getUrl())) {
            wrapper.likeLeft("URL", request.getUrl());
        }
        if (request.getTypeId() != null) {
            wrapper.likeLeft("TYPE_ID", request.getTypeId());
        }
        String time = request.getCreateTime();
        if (StringUtils.isNotEmpty(time)) {
            String beginTime = time + " 00:00:00";
            String endTime = time + " 23:59:59";
            wrapper.between("CREATE_TIME", beginTime, endTime);
        }
        wrapper.orderByDesc("CREATE_TIME");
        Page<BrowserLog> browserLogPage1 = browserLogMapper.selectPage(browserLogPage, wrapper);
        return R.success(browserLogPage1);
    }

    /**
     * 新浏览器行为日志统计接口 一级目录
     *
     * @param request 请求参数
     * @return 响应参数
     */
    @Override
    public R browserActionLogList(BrowserLogPageRequest request) {
        List<Map<String, Object>> logList = browserActionLogMapper.browserActionLogList(request);
        List<Map<String, Object>> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(logList)) {
            for (int i = 0; i < logList.size(); i++) {
                Map<String, Object> map = logList.get(i);
                Object o = map.get("MAC");
                if (o == null) {
                    o = "0";
                }
                String mac = o.toString();
                Date date = browserActionLogMapper.selectMaxTimeByMac(mac);
                Integer time1 = (int) date.getTime();
                String time = DateUtils.dateToString(date);
                map.put("createTime", time);
                map.put("sort", time1);
                map.put("mac",o);
                result.add(map);
            }
        } else {
            result = logList;
        }
        //根据时间排序
        List<Map<String, Object>> collect = result.stream().sorted((a, b) -> (int) b.get("sort") - (int) a.get("sort")).collect(Collectors.toList());
        Integer count = browserActionLogMapper.systemLogListCount(request);
        PageResponse response = PageResponse.createResponse(count, collect, request);
        return R.success(response);
    }

    /**
     * 新浏览器行为日志统计接口 二级目录
     *
     * @param request 请求参数
     * @return 响应参数
     */
    @Override
    public R browserActionLog2List(BrowserLogPageRequest request) {
        if (StringUtils.isEmpty(request.getMac())) {
            return R.result(LACK_PARAMETERS, "mac参数不能为空");
        }
        Page<BrowserActionLog> browserLogPage = new Page<>();
        browserLogPage.setSize(request.getOffset());
        browserLogPage.setCurrent(request.getCurrent());
        QueryWrapper<BrowserActionLog> wrapper = new QueryWrapper<>();
        wrapper.eq("MAC", request.getMac());
        if (StringUtils.isNotEmpty(request.getIpAddress())) {
            wrapper.likeLeft("IP_ADDRESS", request.getIpAddress());
        }
        String time = request.getCreateTime();
        if (StringUtils.isNotEmpty(time)) {
            String beginTime = time + " 00:00:00";
            String endTime = time + " 23:59:59";
            wrapper.between("CREATE_TIME", beginTime, endTime);
        }
        wrapper.orderByDesc("CREATE_TIME");
        Page<BrowserActionLog> browserLogPage1 = browserActionLogMapper.selectPage(browserLogPage, wrapper);
        return R.success(browserLogPage1);
    }
}
