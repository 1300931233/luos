package com.luos.console.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luos.common.layui.LayuiTableResult;
import com.luos.console.system.entity.SysLoginLog;

/**
 * 服务类
 */
public interface SysLoginLogService extends IService<SysLoginLog> {

    /**
     * 分页查询
     */
    LayuiTableResult list(Integer page, Integer limit, SysLoginLog sysLoginLog);

    /**
     * 保存登录日志
     */
    void save(String account, Integer status, String descript);
}
