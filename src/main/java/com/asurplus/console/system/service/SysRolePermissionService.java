package com.asurplus.console.system.service;

import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * 角色-权限关系表 服务类
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 角色授权
     */
    ResponseResult auth(Integer id, String perms);

    /**
     * 登录时，根据用户id查询所有的权限标识
     */
    Set<String> listRolePermissionByUserId(Integer userId);
}
