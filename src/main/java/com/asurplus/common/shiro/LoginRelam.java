package com.asurplus.common.shiro;

import com.asurplus.console.system.entity.SysManager;
import com.asurplus.console.system.service.SysManagerService;
import com.asurplus.console.system.service.SysRolePermissionService;
import com.asurplus.console.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 登录授权
 */
public class LoginRelam extends AuthorizingRealm {

    @Autowired
    private SysManagerService sysManagerService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 身份认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取基于用户名和密码的令牌：实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //根据用户名查找到用户信息
        LambdaQueryWrapper<SysManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysManager::getSmanAccount, token.getUsername());
        SysManager userInfo = sysManagerService.getOne(queryWrapper);
        // 没找到帐号
        if (null == userInfo) {
            throw new UnknownAccountException();
        }
        // 校验用户状态
        if (userInfo.getSmanStatus().equals(1)) {
            throw new DisabledAccountException();
        }
        // 认证缓存信息
        return new SimpleAuthenticationInfo(userInfo, userInfo.getSmanPassword(), ByteSource.Util.bytes(userInfo.getSmanAccount()), getName());
    }

    /**
     * 角色授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysManager sysManager = (SysManager) principalCollection.getPrimaryPrincipal();
        if (null != sysManager) {
            // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            // 如果是超级管理员
            if (1 == sysManager.getSmanId()) {
                simpleAuthorizationInfo.addRole("administrator");
                simpleAuthorizationInfo.addStringPermission("*:*:*");
            } else {
                // 获得用户角色列表
                simpleAuthorizationInfo.addRoles(sysUserRoleService.listUserRoleByUserId(sysManager.getSmanId()));
                // 获得权限列表
                simpleAuthorizationInfo.addStringPermissions(sysRolePermissionService.listRolePermissionByUserId(sysManager.getSmanId()));
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }

    /**
     * 自定义加密规则
     *
     * @param credentialsMatcher
     */
    /*@Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        // 自定义认证加密方式
        CustomCredentialsMatcher customCredentialsMatcher = new CustomCredentialsMatcher();
        // 设置自定义认证加密方式
        super.setCredentialsMatcher(customCredentialsMatcher);
    }*/
}
