package com.asurplus.console.system.service;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.console.system.entity.SysLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

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
