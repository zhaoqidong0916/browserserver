package com.ht.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.common.entity.AuthResources;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
public interface AuthResourcesMapper extends BaseMapper<AuthResources> {

    List<AuthResources> selectAllForLeftSideBar();

    List<AuthResources> selectForLeftSideBar(@Param("roleId") String roleId);
    
    @Select({"select * from auth_resources where FLAG = #{flag} "})
    AuthResources selectByFlag(String paramString);
}
