package com.ht.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.common.entity.BrowserLog;
import com.ht.common.request.log.browserlog.BrowserLogPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-10
 */
public interface BrowserLogMapper extends BaseMapper<BrowserLog> {

    List<Map<String,Object>> systemLogList(@Param("request") BrowserLogPageRequest request);

    Date selectMaxTimeByMac(@Param("mac") String mac);

    Integer systemLogListCount(@Param("request") BrowserLogPageRequest request);
}
