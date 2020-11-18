package com.ht.common.service;

import com.ht.common.entity.BrowserLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.page.R;
import com.ht.common.request.log.browserlog.BrowserLogPageRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-10
 */
public interface IBrowserLogService extends IService<BrowserLog> {

    R systemLogList(BrowserLogPageRequest request);

    R systemLog2List(BrowserLogPageRequest request);

    R browserActionLogList(BrowserLogPageRequest request);

    R browserActionLog2List(BrowserLogPageRequest request);
}
