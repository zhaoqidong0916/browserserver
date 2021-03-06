package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.service.ISystemOperateLogService;
import com.ht.common.dao.SystemOperateLogMapper;
import com.ht.common.entity.SystemOperateLog;
import com.ht.common.page.R;
import com.ht.common.request.log.systemlog.SystemLogPageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
@Service
public class SystemOperateLogServiceImpl extends ServiceImpl<SystemOperateLogMapper, SystemOperateLog> implements ISystemOperateLogService {

    @Autowired
    private SystemOperateLogMapper systemOperateLogMapper;
    @Override
    public R operateLogList(SystemLogPageRequest request) {
        Page<SystemOperateLog> page = new Page<>();
        page.setSize(request.getOffset());
        page.setCurrent(request.getCurrent());
        QueryWrapper<SystemOperateLog> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(request.getIpAddress())){
            wrapper.like("IP_ADDRESS",request.getIpAddress());
        }
        if(StringUtils.isNotEmpty(request.getLoginName())){
            wrapper.like("LOGIN_NAME",request.getLoginName());
        }
        if(StringUtils.isNotEmpty(request.getValue())){
            wrapper.like("VALUE",request.getValue());
        }
        String time = request.getCreateTime();
        if(StringUtils.isNotEmpty(time)){
            String beginTime = time + " 00:00:00";
            String endTime = time + " 23:59:59";
            wrapper.between("CREATE_TIME", beginTime, endTime);
        }
        wrapper.orderByDesc("CREATE_TIME");
        Page<SystemOperateLog> result = systemOperateLogMapper.selectPage(page, wrapper);
        return R.success(result);
    }
}
