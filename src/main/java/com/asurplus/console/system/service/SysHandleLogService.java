package com.asurplus.console.system.service;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.console.system.entity.SysHandleLog;
import com.asurplus.console.system.vo.SysHandleLogVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *  服务类
 */
public interface SysHandleLogService extends IService<SysHandleLog> {

    /**
     * 分页查询
     */
    LayuiTableResult list(Integer page, Integer limit, SysHandleLogVO sysHandleLog);

    /**
     * 新增操作日志记录
     * @param sysHandleLog
     */
    void add (SysHandleLog sysHandleLog);
}

