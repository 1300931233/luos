package com.asurplus.console.system.service;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.console.system.entity.SysDataTableColumn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *  服务类
 */
public interface SysDataTableColumnService extends IService<SysDataTableColumn> {

    /**
     * 分页查询查询所有数据表信息
     */
    LayuiTableResult list(Integer page, Integer limit, SysDataTableColumn sysDataTableColumn);
}
