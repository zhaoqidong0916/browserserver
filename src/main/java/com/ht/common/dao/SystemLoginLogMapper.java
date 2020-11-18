package com.ht.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.common.entity.SystemLoginLog;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
public interface SystemLoginLogMapper extends BaseMapper<SystemLoginLog> {

    @Select("SELECT max(CREATE_TIME) FROM system_login_log")
    Date selectRecentlyTime();
}
