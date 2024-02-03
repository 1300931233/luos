package com.asurplus.console.system.service;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 角色信息表 服务类
 */
public interface SysRoleInfoService extends IService<SysRole> {

    /**
     * 分页查询
     */
    LayuiTableResult list(Integer page, Integer limit, SysRole sysRole);

    /**
     * 新增
     */
    ResponseResult add(SysRole sysRole);

    /**
     * 编辑
     */
    ResponseResult update(SysRole sysRole);

    /**
     * 删除
     */
    ResponseResult delete(Integer id);

    /**
     * 禁/启用
     */
    ResponseResult updateStatus(Integer id, Integer status);

    /**
     * 根据用户id查询角色，给xmselect赋值
     */
    ResponseResult listXmSelectPojo(Integer userId);
}
