package com.ht.common.service;

import com.ht.common.entity.BrowserPluginType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.page.R;
import com.ht.common.request.upgrade.plugintype.PluginTypeAddRequest;
import com.ht.common.request.upgrade.plugintype.PluginTypePageRequest;
import com.ht.common.request.upgrade.plugintype.PluginTypeUpdateRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-16
 */
public interface IBrowserPluginTypeService extends IService<BrowserPluginType> {

    R pluginTypeAdd(PluginTypeAddRequest request);

    R pluginTypeUpdate(PluginTypeUpdateRequest request);

    R pluginTypeList(PluginTypePageRequest request);

    R pluginTypeDelete(String id);

    R pluginTypeDetail(String id);
}
