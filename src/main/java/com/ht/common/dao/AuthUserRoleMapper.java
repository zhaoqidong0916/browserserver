package com.ht.common.dao;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.common.entity.AuthUserRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
public interface AuthUserRoleMapper extends BaseMapper<AuthUserRole> {
	 @Select({"select * from auth_user_role where USER_ID = #{userId} "})
	 AuthUserRole selectByRoId(String paramString);
}
