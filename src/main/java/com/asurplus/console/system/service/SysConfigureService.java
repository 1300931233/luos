package com.asurplus.console.system.service;

import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysConfigure;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统设置 服务类
 */
public interface SysConfigureService extends IService<SysConfigure> {

    /**
     * 获取系统配置，没有则新增
     */
    SysConfigure saveSysConfigure();

    /**
     * 修改系统设置
     */
    ResponseResult update(SysConfigure sysConfigure);
}
