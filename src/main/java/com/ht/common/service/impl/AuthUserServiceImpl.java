package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.service.IAuthUserService;
import com.ht.common.shiro.util.PasswordHelper;
import com.ht.common.dao.AuthResourcesMapper;
import com.ht.common.dao.AuthRoleMapper;
import com.ht.common.dao.AuthRoleResourcesMapper;
import com.ht.common.dao.AuthUserMapper;
import com.ht.common.dao.AuthUserRoleMapper;
import com.ht.common.dao.SystemLoginLogMapper;
import com.ht.common.entity.AuthResources;
import com.ht.common.entity.AuthRole;
import com.ht.common.entity.AuthRoleResources;
import com.ht.common.entity.AuthUser;
import com.ht.common.entity.AuthUserRole;
import com.ht.common.entity.SystemLoginLog;
import com.ht.common.page.PageResponse;
import com.ht.common.page.R;
import com.ht.common.request.system.user.*;
import com.ht.common.utils.DateUtils;
import com.ht.common.utils.IPUtils;
import com.ht.common.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ht.common.enums.ResponseEnum.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements IAuthUserService {


    @Autowired
    private AuthUserMapper authUserMapper;
    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;
    @Autowired
    private SystemLoginLogMapper loginLogMapper;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private AuthRoleMapper authRoleMapper;
    @Autowired
    private AuthResourcesMapper authResourcesMapper;
    @Autowired
    private AuthRoleResourcesMapper authRoleResourcesMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R login(LoginRequest request) {
        if (StringUtils.isEmpty(request.getPassword()) && StringUtils.isEmpty(request.getLoginName())) {
            return R.result(USERNAME_PASSWORD_NULL);
        }
        AuthUser authUser = authUserMapper.selectOne(new QueryWrapper<AuthUser>().eq("LOGIN_NAME", request.getLoginName()));
//        AuthUser authUser = authUserMapper.selectByLoginName(request.getLoginName());
        if (authUser == null) {
            return R.result(UNREGISTERED);
        }
        String encryptPassword = PasswordHelper.encryptPassword(request.getLoginName(), request.getPassword());
        if (!authUser.getPassword().equals(encryptPassword)) {
            return R.result(WRONG_PASSWORD);
        }

        AuthUserRole authUserRole = authUserRoleMapper.selectOne(new QueryWrapper<AuthUserRole>().eq("USER_ID", authUser.getId()).
                eq("CHECK_STATUS", 0));
        if (authUserRole == null) {
            return R.result(ROLE_ERROR);
        }
        String roleId = authUserRole.getRoleId();
        Integer count = authRoleMapper.selectCount(new QueryWrapper<AuthRole>().eq("ID", roleId).eq("CHECK_STATUS", 0));
        if (count == 0) {
            return R.result(ROLE_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                request.getLoginName(), PasswordHelper.encryptPassword(request.getLoginName(), request.getPassword()));
        subject.login(token);
        String s = subject.getSession().getId().toString();
        HashMap<String, String> map = new HashMap<>(8);
        map.put("loginUser", authUser.getLoginName());
        map.put("id", authUser.getId());
        map.put("token", s);
        SystemLoginLog loginLog = new SystemLoginLog();
        loginLog.setIpAddress(IPUtils.getIp(httpServletRequest));
        loginLog.setLoginName(request.getLoginName());
        loginLog.setCreateTime(new Timestamp(System.currentTimeMillis()));
        loginLog.setValue(DateUtils.localDateTimeToString(LocalDateTime.now()) + " 登录此系统");
        loginLog.setId(UUIDUtil.getUUID());
        loginLogMapper.insert(loginLog);
        return R.result(SUCCESS, map);

    }

    @Override
    public R logout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return R.result(SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R register(UserRegisterRequest request) {
        if (authUserMapper.selectCount(new QueryWrapper<AuthUser>().eq("LOGIN_NAME", request.getLoginName())) > 0) {
            return R.result(USER_EXIST);
        }
        String userId = UUIDUtil.getUUID();
        AuthUser authUser = new AuthUser();
        authUser.setId(userId);
        authUser.setPassword(PasswordHelper.encryptPassword(request.getLoginName(), request.getPassword()));
        authUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
        authUser.setEmail(request.getEmail());
        authUser.setLoginName(request.getLoginName());
        authUser.setUsername(request.getUsername());
        authUser.setPhone(request.getPhone());
        authUser.setSex(request.getSex());
        authUserMapper.insert(authUser);
        //用户添加角色
        if (StringUtils.isNotEmpty(request.getRoleId())) {
            AuthUserRole authUserRole = new AuthUserRole();
            authUserRole.setId(UUIDUtil.getUUID());
            authUserRole.setRoleId(request.getRoleId());
            authUserRole.setUserId(userId);
            authUserRole.setUserName(request.getLoginName());
            authUserRole.setRoleName(request.getRoleName());
            authUserRoleMapper.insert(authUserRole);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        return R.success(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R userUpdate(UserUpdateRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", request.getUserId());
        //用户编辑
        AuthUser authUser = new AuthUser();
        authUser.setSex(request.getSex());
        authUser.setPhone(request.getPhone());
        authUser.setUsername(request.getUsername());
        authUser.setEmail(request.getEmail());
        authUser.setId(request.getUserId());
        authUserMapper.updateById(authUser);
        //用户更新角色
        if (StringUtils.isNotEmpty(request.getRoleId()) && StringUtils.isNotEmpty(request.getRoleName())) {
            AuthUserRole authUserRole = authUserRoleMapper.selectOne(new QueryWrapper<AuthUserRole>().eq("USER_ID", request.getUserId()));
            authUserRole.setRoleId(request.getRoleId());
            authUserRole.setRoleName(request.getRoleName());
            authUserRoleMapper.updateById(authUserRole);
        }
        return R.success(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R userDelete(String userId) {
        authUserMapper.deleteById(userId);
        authUserRoleMapper.delete(new QueryWrapper<AuthUserRole>().eq("USER_ID", userId));
        return R.success();
    }

    @Override
    public R userDetail(String userId) {
        AuthUser authUser = authUserMapper.selectById(userId);
        AuthUserRole authUserRole = authUserRoleMapper.selectOne(new QueryWrapper<AuthUserRole>().eq("USER_ID", userId).
                eq("CHECK_STATUS", 0));
        authUser.setRoleName(authUserRole.getRoleName());
        authUser.setRoleId(authUserRole.getRoleId());
        return R.success(authUser);
    }

    @Override
    public R userList(UserPageRequest request) {
        List<AuthUser> authUsers = authUserMapper.userList(request);
        Integer count = authUserMapper.userListCount(request);
        return R.success(PageResponse.createResponse(count, authUsers, request));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R resetPassword(ResetPasswordRequest request, HttpServletRequest httpServletRequest) {
        AuthUser authUser = authUserMapper.selectById(request.getId());

        if (!PasswordHelper.encryptPassword(authUser.getLoginName(), request.getOldPassword()).equals(authUser.getPassword())) {
            return R.result(OLD_PASSWORD_ERROR);
        }
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            return R.result(PASSWORD_NOT_EQUAL);
        }

        authUser.setPassword(PasswordHelper.encryptPassword(authUser.getLoginName(), request.getNewPassword()));
        authUserMapper.updateById(authUser);
        logout(httpServletRequest);
        return R.success();
    }

    @Override
    public R selectRoleListForUser() {
        return R.success(authRoleMapper.selectList(new QueryWrapper<AuthRole>().eq("CHECK_STATUS", 0)));
    }

	@Override
	public boolean autoFlag(ServletRequest request) {
		 HttpServletRequest request2 = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		    HttpServletRequest httpR = (HttpServletRequest)request;
		    String usersessionid = request2.getUserPrincipal().getName();
		    String[] strarr = usersessionid.split(",");
		    System.out.println(strarr);
		    String m = strarr[1];
		    String[] c = m.split("=");
		    String userId = c[1];
		    String flag = httpR.getRequestURI();
		    
		    System.out.println(userId + "++++++++++++++++++++++++");
		    System.out.println(httpR.getRequestURI() + "=========================");
		    
		    AuthUserRole authuserRole = this.authUserRoleMapper.selectByRoId(userId);
		    String roleId = authuserRole.getRoleId();
		    if (authuserRole.getRoleName().equals("admin")) {
		      return true;
		    }
		    List<AuthRoleResources> ResourceId = this.authRoleResourcesMapper.selectAllResourceId(roleId);
		    
		    AuthResources authResources = this.authResourcesMapper.selectByFlag(flag);
		    if (authResources != null){
		 
		      String resourceId = authResources.getId();
		      for (AuthRoleResources attribute : ResourceId){
		     
		        System.out.println(attribute.getResourceId() + "===========");
		        if (resourceId.equals(attribute.getResourceId())) {
		        	
		                 return true;
		          }
		       }
		                return false;
		     }
		                return false;
	  }
}
