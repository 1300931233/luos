package com.luos.console.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luos.common.layui.LayuiTableResult;
import com.luos.console.system.entity.SysHandleLog;
import com.luos.console.system.vo.SysHandleLogVO;

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

