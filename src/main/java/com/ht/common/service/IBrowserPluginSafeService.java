package com.ht.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.entity.BrowserPluginSafe;
import com.ht.common.page.R;
import com.ht.common.request.safe.plugin.PluginAddRequest;
import com.ht.common.request.safe.plugin.PluginPageRequest;
import com.ht.common.request.safe.plugin.PluginStateEditRequest;
import com.ht.common.request.safe.plugin.PluginUpdateRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
public interface IBrowserPluginSafeService extends IService<BrowserPluginSafe> {

    R pluginAdd(PluginAddRequest request);

    R pluginUpdate(PluginUpdateRequest request);

    R pluginList(PluginPageRequest request);

    R pluginDelete(String id);

    R pluginDetail(String id);

    R pluginStateEdit(PluginStateEditRequest request);
}
