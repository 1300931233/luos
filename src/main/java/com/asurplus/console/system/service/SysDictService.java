package com.asurplus.console.system.service;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 服务类
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 分页查询
     */
    LayuiTableResult list(Integer page, Integer limit, SysDict sysDict);

    /**
     * 新增
     */
    ResponseResult add(SysDict sysDict);

    /**
     * 编辑
     */
    ResponseResult update(SysDict sysDict);

    /**
     * 删除
     */
    ResponseResult delete(Integer id);

    /**
     * 禁/启用
     */
    ResponseResult updateStatus(Integer id, Integer status);
}
