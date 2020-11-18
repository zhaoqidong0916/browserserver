package com.ht.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.common.entity.AuthRoleResources;
import com.ht.common.request.system.role.ResourceParam;
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
public interface AuthRoleResourcesMapper extends BaseMapper<AuthRoleResources> {

    void roleResourcesBatchInsert(@Param("request") List<ResourceParam> params);
    @Select({"select RESOURCE_ID from auth_role_resources where ROLE_ID = #{roleId} "})
    List<AuthRoleResources> selectAllResourceId(String paramString);
}
