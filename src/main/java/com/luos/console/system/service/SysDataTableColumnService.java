package com.luos.console.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luos.common.layui.LayuiTableResult;
import com.luos.console.system.entity.SysDataTableColumn;

/**
 *  服务类
 */
public interface SysDataTableColumnService extends IService<SysDataTableColumn> {

    /**
     * 分页查询查询所有数据表信息
     */
    LayuiTableResult list(Integer page, Integer limit, SysDataTableColumn sysDataTableColumn);
}
