package com.ht.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.common.entity.AuthUser;
import com.ht.common.request.system.user.UserPageRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
public interface AuthUserMapper extends BaseMapper<AuthUser> {

    List<AuthUser> userList(@Param("request") UserPageRequest request);

    Integer userListCount(@Param("request") UserPageRequest request);
    
    @Select("select * from auth_user where LOGIN_NAME = #{loginName} ")
    AuthUser selectByLoginName(String loginName);
}
