package com.luos.console.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysConfigure;

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
