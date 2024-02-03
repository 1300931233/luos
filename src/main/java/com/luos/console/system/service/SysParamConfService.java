package com.luos.console.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysParamConf;

/**
 * 系统参数设置 服务类
 */
public interface SysParamConfService extends IService<SysParamConf> {

    /**
     * 获取参数配置
     */
    SysParamConf saveGetSysParamConf();

    /**
     * 修改参数配置
     */
    ResponseResult updateSysParamConf(SysParamConf sysParamConf);
}
