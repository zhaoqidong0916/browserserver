package com.ht.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.common.config.logaop.operatelogaop.OperateLogger;
import com.ht.common.config.logaop.operatelogaop.OperateType;
import com.ht.common.service.IAuthResourcesService;
import com.ht.common.dao.AuthResourcesMapper;
import com.ht.common.dao.AuthRoleMapper;
import com.ht.common.dao.AuthUserRoleMapper;
import com.ht.common.entity.AuthResources;
import com.ht.common.entity.AuthRole;
import com.ht.common.entity.AuthUserRole;
import com.ht.common.page.R;
import com.ht.common.request.system.resources.ResourcesAddRequest;
import com.ht.common.request.system.resources.ResourcesEnableRequest;
import com.ht.common.request.system.resources.ResourcesPageRequest;
import com.ht.common.request.system.resources.ResourcesUpdateRequest;
import com.ht.common.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ht.common.enums.ResponseEnum.RESOURCE_NAME_REPEAT;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-05
 */
@Service
public class AuthResourcesServiceImpl extends ServiceImpl<AuthResourcesMapper, AuthResources> implements IAuthResourcesService {

    @Autowired
    private AuthResourcesMapper resourcesMapper;
    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;
    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "添加左侧树资源", type = OperateType.add)
    public R resourceAdd(ResourcesAddRequest request) {
        //验证是否有重复数据,有的话不让添加重复数据
        R check = check(request, ResourcesAddRequest.class);
        if (check != null) {
            return check;
        }
        AuthResources authResources = new AuthResources();
        authResources.setId(UUIDUtil.getUUID());
        authResources.setHtmlUrl(request.getHtmlUrl());
        authResources.setParentName(request.getParentName());
        authResources.setSecondSort(request.getSecondSort());
        authResources.setSort(request.getSort());
        authResources.setUrlName(request.getUrlName());
        authResources.setIconType("-1");
        authResources.setCreateTime(new Timestamp(System.currentTimeMillis()));
        authResources.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        resourcesMapper.insert(authResources);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "修改左侧树资源", type = OperateType.update)
    public R resourceUpdate(ResourcesUpdateRequest request) {
        //验证是否有重复数据,有的话不让添加重复数据
        R check = check(request, ResourcesUpdateRequest.class);
        if (check != null) {
            return check;
        }
        AuthResources authResources = new AuthResources();
        authResources.setId(request.getId());
        authResources.setHtmlUrl(request.getHtmlUrl());
        authResources.setParentName(request.getParentName());
        authResources.setSecondSort(request.getSecondSort());
        authResources.setSort(request.getSort());
        authResources.setUrlName(request.getUrlName());
        authResources.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        resourcesMapper.updateById(authResources);
        return R.success();
    }

    @Override
    public R resourceList(ResourcesPageRequest request) {
        Page<AuthResources> browserVisitSafePage = new Page<>();
        browserVisitSafePage.setSize(request.getOffset());
        browserVisitSafePage.setCurrent(request.getCurrent());
        QueryWrapper<AuthResources> browserVisitSafeQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getParentName())) {
            browserVisitSafeQueryWrapper.like("PARENT_NAME", request.getParentName());
        }
        if (StringUtils.isNotEmpty(request.getUrlName())) {
            browserVisitSafeQueryWrapper.like("URL_NAME", request.getUrlName());
        }
        browserVisitSafeQueryWrapper.orderByDesc("CREATE_TIME");
        Page<AuthResources> result = resourcesMapper.selectPage(browserVisitSafePage, browserVisitSafeQueryWrapper);
        return R.success(result);
    }


    @Override
    public R resourceSonList(String parentName) {
        List<AuthResources> resources = resourcesMapper.selectList(new QueryWrapper<AuthResources>().eq("PARENT_NAME", parentName));
        return R.success(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "启用禁用左侧树资源", type = OperateType.update)
    public R resourceEnable(ResourcesEnableRequest request) {
        AuthResources authResources = new AuthResources();
        authResources.setId(request.getId());
        authResources.setCheckStatus(request.getCheckStatus());
        authResources.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        resourcesMapper.updateById(authResources);
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLogger(value = "删除左侧树资源", type = OperateType.delete)
    public R resourceDelete(String id) {
        resourcesMapper.deleteById(id);
        return R.success();
    }

    @Override
    public R resourceDetail(String id) {
        return R.success(resourcesMapper.selectById(id));
    }

    @Override
    public R LeftSideBar(String userId) {
        AuthUserRole authUserRole = authUserRoleMapper.selectOne(new QueryWrapper<AuthUserRole>().eq("USER_ID", userId));
        List<Map<String, Object>> leftSideBarResult = getLeftSideBarResult(authUserRole);
        return R.success(leftSideBarResult);
    }




    /**
     * 处理左侧菜单栏权限
     *
     * @param authUserRole
     * @return
     */
    private List<Map<String, Object>> getLeftSideBarResult(AuthUserRole authUserRole) {
        boolean flag = false;
        if (authUserRole == null) {
            return null;
        }
        AuthRole authRole = authRoleMapper.selectById(authUserRole.getRoleId());
        //如果是admin直接返回所有
        if (authRole.getRoleType() == 3) {
            flag = true;
        }
        List<AuthResources> authResources;
        if (flag) {
            // 处理管理员的角色，返回全部
            authResources = resourcesMapper.selectAllForLeftSideBar();
        } else {
            // 处理其他的角色
            authResources = resourcesMapper.selectForLeftSideBar(authUserRole.getRoleId());
        }

        Map<String, List<AuthResources>> parentName = authResources.
                stream().collect(Collectors.groupingBy(AuthResources::getParentName));

        Iterator<Map.Entry<String, List<AuthResources>>> iterator = parentName.entrySet().iterator();

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = null;
        //处理成前端需要的格式
        while (iterator.hasNext()) {
            map = new HashMap(16);
            Object key = iterator.next().getKey();
            List<AuthResources> resources = parentName.get(key);
            //二级菜单排序,数值越大越靠后
            resources.sort(Comparator.comparingInt(t -> Integer.parseInt(t.getSecondSort().toString())));
            map.put("name", key.toString());
            map.put("sort", resources.get(0).getSort());
            map.put("children", resources);
            map.put("iconType", resources.get(0).getIconType());
            result.add(map);
        }
        //父级菜单排序,数值越大越靠后
        result.sort(Comparator.comparingInt(t -> Integer.parseInt(t.get("sort").toString())));
        return result;
    }


    /**
     * 检查重复数据
     *
     * @param o 参数
     * @param c 参数类class
     * @return
     */
    private R check(Object o, Class c) {
        Integer htmlUrl = 0;
        Integer urlName = 0;
        Integer parentName = 0;
        if (c.equals(ResourcesAddRequest.class)) {
            ResourcesAddRequest request = (ResourcesAddRequest) o;
            htmlUrl = resourcesMapper.selectCount(new QueryWrapper<AuthResources>().eq("HTML_URL", request.getHtmlUrl()));
            urlName = resourcesMapper.selectCount(new QueryWrapper<AuthResources>().eq("URL_NAME", request.getUrlName()));
            parentName = resourcesMapper.selectCount(new QueryWrapper<AuthResources>().eq("PARENT_NAME", request.getParentName()));
        } else if (c.equals(ResourcesUpdateRequest.class)) {
            ResourcesUpdateRequest request = (ResourcesUpdateRequest) o;
            if(request.getParentName()==null||StringUtils.isEmpty(request.getParentName())){
                parentName = resourcesMapper.selectCount(new QueryWrapper<AuthResources>().eq("PARENT_NAME", request.getParentName()));
            }
            if(request.getHtmlUrl()==null||StringUtils.isEmpty(request.getHtmlUrl())){
                htmlUrl = resourcesMapper.selectCount(new QueryWrapper<AuthResources>().eq("HTML_URL", request.getHtmlUrl()));
            }
            if(request.getUrlName()==null||StringUtils.isEmpty(request.getUrlName())){
                urlName = resourcesMapper.selectCount(new QueryWrapper<AuthResources>().eq("URL_NAME", request.getUrlName()));
            }
        }

        if (htmlUrl > 0 || urlName > 0 || parentName > 0) {
            return R.result(RESOURCE_NAME_REPEAT);
        }
        return null;
    }
}
