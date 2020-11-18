package com.ht.common.service;

import com.ht.common.entity.SystemOperateLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.page.R;
import com.ht.common.request.log.systemlog.SystemLogPageRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
public interface ISystemOperateLogService extends IService<SystemOperateLog> {

    R operateLogList(SystemLogPageRequest request);
}
