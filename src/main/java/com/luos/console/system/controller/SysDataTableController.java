package com.luos.console.system.controller;

import com.luos.common.layui.LayuiTableResult;
import com.luos.console.system.entity.SysDataTable;
import com.luos.console.system.service.SysDataTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前端控制器
 **/
@Api(tags = "后台管理端--数据备份")
@Controller
@RequestMapping("/sys/sys-data-table")
public class SysDataTableController {

    @Autowired
    private SysDataTableService sysDataTableService;

    @ApiOperation(value = "请求数据表列表页")
    @GetMapping("init")
    public String init() {
        return "system/datatable/list";
    }

    @ApiOperation(value = "分页查询数据表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "tableName", value = "表名"),
            @ApiImplicitParam(name = "tableComment", value = "表说明")
    })
    @PostMapping("list")
    @ResponseBody
    public LayuiTableResult list(Integer page, Integer limit, SysDataTable sysDataTable) {
        return sysDataTableService.list(page, limit, sysDataTable);
    }
}
