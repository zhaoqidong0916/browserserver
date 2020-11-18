package com.ht.common.service;

import javax.servlet.http.HttpServletRequest;

import com.ht.common.page.R;
import com.ht.common.request.log.browserlog.ActionLogCollectRequest;
import com.ht.common.request.log.browserlog.InfoCollectRequest;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-10 11:46
 **/
public interface ISafeService {
    R safeList(String md5);

    R configList(String md5);

    R infoCollect(InfoCollectRequest request);
    
    boolean headList(HttpServletRequest paramHttpServletRequest);

    R actionLogCollect(ActionLogCollectRequest request);
}
