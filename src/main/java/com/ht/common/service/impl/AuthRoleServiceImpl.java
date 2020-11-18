package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.service.IAuthRoleService;
import com.ht.common.dao.AuthResourcesMapper;
import com.ht.common.dao.AuthRoleMapper;
import com.ht.common.dao.AuthRoleResourcesMapper;
import com.ht.common.dao.AuthUserRoleMapper;
import com.ht.common.entity.AuthResources;
import com.ht.common.entity.AuthRole;
import com.ht.common.entity.AuthRoleResources;
import com.ht.common.entity.AuthUserRole;
import com.ht.common.page.R;
import com.ht.common.request.system.role.*;
import com.ht.common.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleMapper, AuthRole> implements IAuthRoleService {

    @Autowired
    private AuthRoleMapper authRoleMapper;
    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;
    @Autowired
    private AuthRoleResourcesMapper authRoleResourcesMapper;
    @Autowired
    private AuthResourcesMapper authResourcesMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "添加角色", type = OperateType.add)
    public R roleAdd(RolesAddRequest request) {
        //判断角色是否已经存在
        Integer count = authRoleMapper.selectCount(new QueryWrapper<AuthRole>().
                eq("ROLE_NAME", request.getRoleName()));
        if (count > 0) {
            return R.result(ROLE_EXIST);
        }
        AuthRole authRole = new AuthRole();
        authRole.setRoleName(request.getRoleName());
        authRole.setRoleDescription(request.getRoleDescription());
        authRole.setId(UUIDUtil.getUUID());
        authRole.setCreateTime(new Timestamp(System.currentTimeMillis()));
        authRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        authRoleMapper.insert(authRole);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "修改角色", type = OperateType.update)
    public R roleUpdate(RolesUpdateRequest request) {
        //判读角色是否已经被使用
        Integer count1 = authUserRoleMapper.selectCount(new QueryWrapper<AuthUserRole>().eq("ROLE_ID", request.getId()));
        if (count1 > 0) {
            return R.result(ROLE_HAS_USER);
        }
        AuthRole authRole = new AuthRole();
        //判断角色是否已经存在
        if (StringUtils.isNotEmpty(request.getRoleName())) {
            Integer count = authRoleMapper.selectCount(new QueryWrapper<AuthRole>().
                    eq("ROLE_NAME", request.getRoleName()));
            if (count > 0) {
                return R.result(ROLE_EXIST);
            }
            authRole.setRoleName(request.getRoleName());
        }
        authRole.setRoleDescription(request.getRoleDescription());
        authRole.setId(request.getId());
        authRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        authRoleMapper.updateById(authRole);
        return R.success();
    }

    @Override
    public R roleList(RolesPageRequest request) {
        Page<AuthRole> browserVisitSafePage = new Page<>();
        browserVisitSafePage.setSize(request.getOffset());
        browserVisitSafePage.setCurrent(request.getCurrent());
        QueryWrapper<AuthRole> browserVisitSafeQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getRoleName())) {
            browserVisitSafeQueryWrapper.like("ROLE_NAME", request.getRoleName());
        }
        if (StringUtils.isNotEmpty(request.getRoleDescription())) {
            browserVisitSafeQueryWrapper.like("ROLE_DESCRIPTION", request.getRoleDescription());
        }
        browserVisitSafeQueryWrapper.orderByDesc("CREATE_TIME");
        Page<AuthRole> result = (Page<AuthRole>) authRoleMapper.selectPage(browserVisitSafePage, browserVisitSafeQueryWrapper);
        return R.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "启用禁用角色", type = OperateType.update)
    public R roleEnable(RolesEnableRequest request) {
        AuthRole authRole = new AuthRole();
        authRole.setCheckStatus(request.getCheckStatus());
        authRole.setId(request.getId());
        authRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        authRoleMapper.updateById(authRole);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除角色", type = OperateType.delete)
    public R roleDelete(String id) {
        Integer count = authUserRoleMapper.selectCount(new QueryWrapper<AuthUserRole>().eq("ROLE_ID", id));
        if (count > 0) {
            return R.result(ROLE_HAS_USER);
        }
        authRoleMapper.deleteById(id);
        return R.success();
    }

    @Override
    public R roleDetail(String id) {
        return R.success(authRoleMapper.selectById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R roleResourceAdd(RoleResourceAddRequest request) {
        //如果角色处于被禁用状态,不允许修改权限
        Integer count = authRoleMapper.selectCount(new QueryWrapper<AuthRole>().eq("ID", request.getRoleId()).eq("CHECK_STATUS", 1));
        if (count > 0) {
            return R.result(ROLE_IS_FORBBIN);
        }
        //先删除此角色的所有页面权限
        List<ResourceParam> params = request.getParams();
        authRoleResourcesMapper.delete(new QueryWrapper<AuthRoleResources>().eq("ROLE_ID", request.getRoleId()));
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach(t -> {
                t.setResourceRoleId(UUIDUtil.getUUID());
                t.setRoleId(request.getRoleId());
                t.setRoleName(request.getRoleName());
            });
            authRoleResourcesMapper.roleResourcesBatchInsert(params);
        }
        return R.success();
    }

    @Override
    public R roleResourceList(String roleId) {
        //获取全部的权限列表
        List<AuthResources> allResource = authResourcesMapper.selectAllForLeftSideBar();
        //获取当前角色有的权限列表
        List<AuthResources> roleResource = authResourcesMapper.selectForLeftSideBar(roleId);
        AuthRole authRole = authRoleMapper.selectById(roleId);

        List<AuthResources> resources = getResources(allResource, roleResource);
        //如果角色为管理员,默认所有权限开启
        if (authRole.getRoleType() == 3) {
            allResource.forEach(t -> {
                t.setSelectEd(1);
            });
        }
        List<Map<String, Object>> result = getResult(resources);
        return R.success(result);
    }

    /**
     * 数据处理，把改角色有的权限打一个标识，即selectEd = 1。没有的置为0
     * selectEd = 1标识已经勾选   0代表没有勾选
     *
     * @param allResources
     * @param roleResources
     * @return
     */
    private List<AuthResources> getResources(List<AuthResources> allResources, List<AuthResources> roleResources) {
        List<AuthResources> result = new ArrayList<>();
        for (int i = 0; i < allResources.size(); i++) {
            AuthResources authResources = allResources.get(i);
            String htmlUrl = authResources.getHtmlUrl();
            //把所有的资源标记置为0,即所有角色都是没有选中状态
            authResources.setSelectEd(0);
            if (!CollectionUtils.isEmpty(roleResources) || roleResources.size() > 0) {
                //遍历当前角色拥有的页面权限列表,包含的 把selectEd置为1,代表选中状态
                for (int j = 0; j < roleResources.size(); j++) {
                    AuthResources role = roleResources.get(j);
                    String url = role.getHtmlUrl();
                    //htmlUrl字段相同代表选中
                    if (htmlUrl.equals(url)) {
                        authResources.setSelectEd(1);
                    }
                }
            }
            result.add(authResources);
        }
        return result;
    }


    /**
     * 处理成前端需要的格式
     *
     * @param maps
     * @return
     */
    private List<Map<String, Object>> getResult(List<AuthResources> maps) {
        Map<String, List<AuthResources>> collect = maps.stream().collect(Collectors.groupingBy(AuthResources::getParentName));
        Iterator<Map.Entry<String, List<AuthResources>>> iterator = collect.entrySet().iterator();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = null;
        while (iterator.hasNext()) {
            map = new HashMap();
            Object key = iterator.next().getKey();
            List<AuthResources> resources = collect.get(key);
            map.put("resourceType", key.toString());
            map.put("resourceList", resources);
            result.add(map);
        }
        return result;
    }

}
