package com.luos.console.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luos.common.layui.LayuiTableResult;
import com.luos.common.shiro.ShiroUtils;
import com.luos.common.utils.AddressUtils;
import com.luos.common.utils.IPUtils;
import com.luos.common.utils.SpringContextUtils;
import com.luos.console.system.entity.SysLoginLog;
import com.luos.console.system.mapper.SysLoginLogMapper;
import com.luos.console.system.service.SysLoginLogService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 服务实现类
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    @Override
    public LayuiTableResult list(Integer page, Integer limit, SysLoginLog sysLoginLog) {
        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();
        //如果不是超级管理员则只能查看自己的登录操作
        if(ShiroUtils.getSysUserInfo().getSmanId() > 1){
            queryWrapper.like(SysLoginLog::getSlloAccount, ShiroUtils.getSysUserInfo().getSmanAccount());
        }
        if (null != sysLoginLog.getSlloStatus()) {
            queryWrapper.eq(SysLoginLog::getSlloStatus, sysLoginLog.getSlloStatus());
        }
        queryWrapper.orderByDesc(SysLoginLog::getSlloCreateTime);
        return new LayuiTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public void save(String account, Integer status, String descript) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setSlloAccount(account);
        try {
            // 获取request
            HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
            // 获取ip地址
            sysLoginLog.setSlloIp(IPUtils.getIpAddress(request));
            sysLoginLog.setSlloLocation(AddressUtils.getAddressByIP(sysLoginLog.getSlloIp()));
        } catch (Exception e) {
            sysLoginLog.setSlloIp("127.0.0.1");
            sysLoginLog.setSlloLocation("局域网，无法获取位置");
        }
        sysLoginLog.setSlloStatus(status);
        sysLoginLog.setSlloDescript(descript);
        sysLoginLog.setSlloCreateTime(new Date());
        this.save(sysLoginLog);
    }
}
