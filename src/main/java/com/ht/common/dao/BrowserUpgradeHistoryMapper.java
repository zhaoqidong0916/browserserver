package com.ht.common.dao;

import com.ht.common.entity.BrowserUpgradeHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yakun.shi
 * @since 2020-04-27
 */
public interface BrowserUpgradeHistoryMapper extends BaseMapper<BrowserUpgradeHistory> {

    @Select("SELECT max(CREATE_TIME) FROM browser_upgrade_history WHERE TYPE = #{type}")
    Date selectRecentlyTime(@Param("type") Integer type);
}
