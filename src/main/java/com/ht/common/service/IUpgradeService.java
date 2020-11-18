package com.ht.common.service;

import javax.servlet.http.HttpServletRequest;

import com.ht.common.controller.browser.upgrade.request.UpgradeRequest;
import com.ht.common.page.R;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-03-31 11:45
 **/
public interface IUpgradeService {
    R browserUpgrade(String version, Integer systemType);

    R pluginUpgrade(UpgradeRequest request);

    R certUpgrade(UpgradeRequest request);
    
    boolean headList(HttpServletRequest paramHttpServletRequest);
}
