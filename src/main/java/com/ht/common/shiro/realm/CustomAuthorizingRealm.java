package com.ht.common.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.common.exceptions.exception.UserNotExistException;
import com.ht.common.dao.AuthUserMapper;
import com.ht.common.entity.AuthUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @date 2020/3/5 16:23
 * @desc 
 * @author yakun.shi
 */
@Component
public class CustomAuthorizingRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(CustomAuthorizingRealm.class);

    @Autowired
    private AuthUserMapper authUserMapper;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AuthUser authUser = (AuthUser) principalCollection.getPrimaryPrincipal();
        logger.info(authUser.getUsername() + "进行授权操作");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        return info;
    }

    /**
     * 认证回调函数，登录信息和用户验证信息验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //toke强转
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String loginUser = usernamePasswordToken.getUsername();
        //根据用户名查询密码，由安全管理器负责对比查询出的数据库中的密码和页面输入的密码是否一致

        AuthUser user = authUserMapper.selectOne(new QueryWrapper<AuthUser>().eq("LOGIN_NAME",loginUser));
        if (user == null) {
            throw new UserNotExistException("用户不存在");
        }
        String password = user.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getLoginName() + password);
        //最后的比对需要交给安全管理器,三个参数进行初步的简单认证信息对象的包装,由安全管理器进行包装运行
        return new SimpleAuthenticationInfo(user, password, credentialsSalt, getName());
    }
}
