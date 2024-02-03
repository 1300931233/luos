package com.luos.console.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysRolePermission;

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
