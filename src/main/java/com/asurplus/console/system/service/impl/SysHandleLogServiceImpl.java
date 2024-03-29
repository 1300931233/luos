package com.asurplus.console.system.service.impl;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.console.system.entity.SysHandleLog;
import com.asurplus.console.system.mapper.SysHandleLogMapper;
import com.asurplus.console.system.service.SysHandleLogService;
import com.asurplus.console.system.vo.SysHandleLogVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 */
@Service
public class SysHandleLogServiceImpl extends ServiceImpl<SysHandleLogMapper, SysHandleLog> implements SysHandleLogService {

    @Override
    public LayuiTableResult list(Integer page, Integer limit, SysHandleLogVO sysHandleLog) {
        QueryWrapper<SysHandleLogVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.SHLO_DELFLAG", 0);
        if (StringUtils.isNotBlank(sysHandleLog.getCreateUserName())) {
            queryWrapper.like("b.SMAN_NAME", sysHandleLog.getCreateUserName());
        }
        queryWrapper.orderByDesc("a.SHLO_CREATE_TIME");
        return new LayuiTableResult<>(this.baseMapper.listSysHandleLogVO(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public void add(SysHandleLog sysHandleLog) {
        this.save(sysHandleLog);
    }
}
