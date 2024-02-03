package com.luos.console.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luos.common.layui.LayuiTableResult;
import com.luos.console.system.entity.SysDataTable;
import com.luos.console.system.mapper.SysDataTableMapper;
import com.luos.console.system.service.SysDataTableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 */
@Service
public class SysDataTableServiceImpl extends ServiceImpl<SysDataTableMapper, SysDataTable> implements SysDataTableService {

    @Value("${spring.datasource.url}")
    private String database;

    @Override
    public LayuiTableResult list(Integer page, Integer limit, SysDataTable sysDataTable) {
        LambdaQueryWrapper<SysDataTable> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(database)) {
            queryWrapper.eq(SysDataTable::getTableSchema, database.substring(database.indexOf("3306/") + 5, database.indexOf("?")));
        }
        if (StringUtils.isNotBlank(sysDataTable.getTableName())) {
            queryWrapper.like(SysDataTable::getTableName, sysDataTable.getTableName());
        }
        if (StringUtils.isNotBlank(sysDataTable.getTableComment())) {
            queryWrapper.like(SysDataTable::getTableComment, sysDataTable.getTableComment());
        }
        queryWrapper.orderByDesc(SysDataTable::getCreateTime);
        return new LayuiTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public List<SysDataTable> listSysDataTableForSelect() {
        LambdaQueryWrapper<SysDataTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysDataTable::getTableName);
        if (StringUtils.isNotBlank(database)) {
            queryWrapper.eq(SysDataTable::getTableSchema, database.substring(database.indexOf("3306/") + 5, database.indexOf("?")));
        }
        return this.list(queryWrapper);
    }
}
