package com.luos.console.system.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.luos.common.layui.LayuiTableResult;
import com.luos.console.system.entity.SysDataTable;

import java.util.List;

/**
 *  服务类
 */
public interface SysDataTableService extends IService<SysDataTable> {

    /**
     * 分页查询查询所有数据表信息
     */
    LayuiTableResult list(Integer page, Integer limit, SysDataTable sysDataTable);

    /**
     * 查询所有表格数据，提供给下拉选择
     */
    List<SysDataTable> listSysDataTableForSelect();
}
