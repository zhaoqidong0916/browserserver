package com.ht.common.dao;

import com.ht.common.entity.BrowserActionLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.common.request.log.browserlog.BrowserLogPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yang.yang
 * @since 2020-09-22
 */
public interface BrowserActionLogMapper extends BaseMapper<BrowserActionLog> {

    List<Map<String, Object>> browserActionLogList(@Param("request") BrowserLogPageRequest request);

    Date selectMaxTimeByMac(@Param("mac") String mac);

    Integer systemLogListCount(@Param("request") BrowserLogPageRequest request);
}
