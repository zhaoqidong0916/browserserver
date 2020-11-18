package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.controller.browser.safe.request.ErrorCollectRequest;
import com.ht.common.controller.browser.safe.request.ErrorJsCollectRequest;
import com.ht.common.dao.BrowserErrorLogMapper;
import com.ht.common.dao.BrowserJsErrorLogMapper;
import com.ht.common.entity.BrowserErrorLog;
import com.ht.common.entity.BrowserJsErrorLog;
import com.ht.common.page.R;
import com.ht.common.request.log.browserlog.BrowserLogPageRequest;
import com.ht.common.service.IBrowserErrorLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 浏览器错误日志服务类
 */
@Service
public class BrowserErrorLogServiceImpl extends ServiceImpl<BrowserErrorLogMapper, BrowserErrorLog> implements IBrowserErrorLogService {

    @Autowired
    private BrowserErrorLogMapper browserErrorLogMapper;
    @Autowired
    private BrowserJsErrorLogMapper browserJsErrorLogMapper;

    @Override
    public R browserErrorLogList(BrowserLogPageRequest request) throws ParseException {
        Page<BrowserErrorLog> page = new Page<>();
        page.setSize(request.getOffset());
        page.setCurrent(request.getCurrent());
        QueryWrapper<BrowserErrorLog> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(request.getIpAddress())){
            wrapper.like("IP_ADDRESS",request.getIpAddress());
        }
        if(StringUtils.isNotEmpty(request.getMac())){
            wrapper.like("MAC",request.getMac());
        }
        if (StringUtils.isNotEmpty(request.getBrowserVersion())) {
            wrapper.likeLeft("BROWSER_VERSION", request.getBrowserVersion());
        }
        String time = request.getCreateTime();
        if(StringUtils.isNotEmpty(time)){
            String beginTime = time + " 00:00:00";
            String endTime = time + " 23:59:59";
            Date newBeginDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginTime);
            Date newEndDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
            wrapper.between("CREATE_TIME", newBeginDate, newEndDate);
        }
        wrapper.orderByDesc("CREATE_TIME");
        Page<BrowserErrorLog> result = browserErrorLogMapper.selectPage(page, wrapper);
        return R.success(result);
    }

    /**
     * 添加浏览器上送的客户端错误日志
     *
     * @param request
     * @return
     */
    @Override
    public R errorCollect(ErrorCollectRequest request) {
        BrowserErrorLog browserErrorLog = new BrowserErrorLog();
        browserErrorLog.setIpAddress(request.getIpAddress());
        browserErrorLog.setMac(request.getMac());
        browserErrorLog.setContent(request.getContent());
        browserErrorLog.setCreateTime(new Date());
        browserErrorLog.setUpdateTime(new Date());
        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();
        browserErrorLog.setDay(day);
        browserErrorLog.setMonth(month);
        browserErrorLog.setYear(year);
        browserErrorLogMapper.insert(browserErrorLog);
        return R.success();
    }

    /**
     * 添加浏览器上送的JS错误日志
     *
     * @param request
     * @return
     */
    @Override
    public R errorJsCollect(ErrorJsCollectRequest request) {
        BrowserJsErrorLog browserJsErrorLog = new BrowserJsErrorLog();
        browserJsErrorLog.setIpAddress(request.getIpAddress());
        browserJsErrorLog.setMac(request.getMac());
        browserJsErrorLog.setContent(request.getContent());
        browserJsErrorLog.setRowNum(request.getRowNum());
        browserJsErrorLog.setFileName(request.getFileName());
        browserJsErrorLog.setCreateTime(new Date());
        browserJsErrorLogMapper.insert(browserJsErrorLog);
        return R.success();
    }

    /**
     * 查询JS错误日志列表
     */
    @Override
    public R browserJsErrorLogList(BrowserLogPageRequest request) throws ParseException {
        Page<BrowserJsErrorLog> page = new Page<>();
        page.setSize(request.getOffset());
        page.setCurrent(request.getCurrent());
        QueryWrapper<BrowserJsErrorLog> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(request.getIpAddress())){
            wrapper.like("IP_ADDRESS",request.getIpAddress());
        }
        if(StringUtils.isNotEmpty(request.getMac())){
            wrapper.like("MAC",request.getMac());
        }
        String time = request.getCreateTime();
        if(StringUtils.isNotEmpty(time)){
            String beginTime = time + " 00:00:00";
            String endTime = time + " 23:59:59";
            Date newBeginDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginTime);
            Date newEndDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
            wrapper.between("CREATE_TIME", newBeginDate, newEndDate);
        }
        wrapper.orderByDesc("CREATE_TIME");
        Page<BrowserJsErrorLog> result = browserJsErrorLogMapper.selectPage(page, wrapper);
        return R.success(result);
    }
}