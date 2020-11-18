package com.ht.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.controller.browser.safe.request.ErrorCollectRequest;
import com.ht.common.controller.browser.safe.request.ErrorJsCollectRequest;
import com.ht.common.entity.BrowserErrorLog;
import com.ht.common.page.R;
import com.ht.common.request.log.browserlog.BrowserLogPageRequest;

import java.text.ParseException;

/**
 * 浏览器错误日志服务接口
 */
public interface IBrowserErrorLogService extends IService<BrowserErrorLog> {

    R browserErrorLogList(BrowserLogPageRequest request) throws ParseException;

    R errorCollect(ErrorCollectRequest request);

    R errorJsCollect(ErrorJsCollectRequest request);

    R browserJsErrorLogList(BrowserLogPageRequest request) throws ParseException;
}