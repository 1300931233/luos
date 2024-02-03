package com.asurplus.console.system.service;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysManager;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户信息表 服务类
 */
public interface SysManagerService extends IService<SysManager> {

    /**
     * 分页查询
     */
    LayuiTableResult list(Integer page, Integer limit, SysManager sysManager);

    /**
     * 新增
     */
    ResponseResult add(SysManager sysManager);

    /**
     * 编辑
     */
    ResponseResult update(SysManager sysManager);

    /**
     * 编辑个人资料
     */
    ResponseResult info(SysManager sysManager);

    /**
     * 删除
     */
    ResponseResult delete(Long id);

    /**
     * 禁/启用
     */
    ResponseResult updateStatus(Long id, Integer status);

    /**
     * 修改密码
     */
    ResponseResult updatePassword(String oldPassword, String password);

    /**
     * 修改密码
     */
    ResponseResult updatePassword(Integer id, String password);
}
