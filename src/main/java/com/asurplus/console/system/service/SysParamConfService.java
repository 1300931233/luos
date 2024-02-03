package com.asurplus.console.system.service;

import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysParamConf;
import com.baomidou.mybatisplus.extension.service.IService;

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
