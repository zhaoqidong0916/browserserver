package com.ht.common.service;

import javax.servlet.http.HttpServletRequest;

import com.ht.common.page.R;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-11 15:27
 **/
public interface IApplicationService {
    R resourceList();

    R urlList();
    boolean headList(HttpServletRequest paramHttpServletRequest);
}
