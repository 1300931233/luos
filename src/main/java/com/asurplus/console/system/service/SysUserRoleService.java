package com.asurplus.console.system.service;

import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysManagerRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * 用户-角色关系表 服务类
 */
public interface SysUserRoleService extends IService<SysManagerRole> {

    /**
     * 登录时，根据用户id查询全部角色标识
     */
    Set<String> listUserRoleByUserId(Integer userId);

    /**
     * 授权
     */
    ResponseResult auth(Integer id, String roles);
}
