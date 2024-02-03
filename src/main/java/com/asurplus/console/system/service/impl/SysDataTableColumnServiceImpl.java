package com.asurplus.console.system.service.impl;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.console.system.entity.SysDataTableColumn;
import com.asurplus.console.system.mapper.SysDataTableColumnMapper;
import com.asurplus.console.system.service.SysDataTableColumnService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 */
@Service
public class SysDataTableColumnServiceImpl extends ServiceImpl<SysDataTableColumnMapper, SysDataTableColumn> implements SysDataTableColumnService {

    @Value("${spring.datasource.url}")
    private String database;

    @Override
    public LayuiTableResult list(Integer page, Integer limit, SysDataTableColumn sysDataTableColumn) {
        LambdaQueryWrapper<SysDataTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(database)) {
            queryWrapper.eq(SysDataTableColumn::getTableSchema, database.substring(database.indexOf("3306/") + 5, database.indexOf("?")));
        }
        if (StringUtils.isNotBlank(sysDataTableColumn.getTableName())) {
            queryWrapper.like(SysDataTableColumn::getTableName, sysDataTableColumn.getTableName());
        }
        if (StringUtils.isNotBlank(sysDataTableColumn.getColumnComment())) {
            queryWrapper.like(SysDataTableColumn::getColumnComment, sysDataTableColumn.getColumnComment());
        }
        return new LayuiTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }
}
