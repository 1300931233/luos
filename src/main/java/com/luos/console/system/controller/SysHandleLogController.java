package com.luos.console.system.controller;


import com.luos.common.layui.LayuiTableResult;
import com.luos.common.log.OperationLogAnnotate;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysHandleLog;
import com.luos.console.system.service.SysHandleLogService;
import com.luos.console.system.vo.SysHandleLogVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 前端控制器
 */
@Controller
@RequestMapping("/sys/sys-handle-log")
public class SysHandleLogController {

    @Autowired
    private SysHandleLogService sysHandleLogService;

    @ApiOperation(value = "请求操作日志列表页")
    @GetMapping("init")
    public String init() {
        return "system/handlelog/list";
    }

    @ApiOperation(value = "分页查询操作日志数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "account", value = "操作账户")
    })
    @PostMapping("list")
    @ResponseBody
    public LayuiTableResult list(Integer page, Integer limit, SysHandleLogVO sysHandleLog) {
        return sysHandleLogService.list(page, limit, sysHandleLog);
    }

    @ResponseBody
    @ApiOperation(value = "批量删除操作日志")
    @PostMapping("batchDelete")
    @OperationLogAnnotate(shloModem="批量删除操作日志")
    public ResponseResult batchDelete(@RequestBody List<SysHandleLog> sysHandleLog){
        sysHandleLogService.removeByIds(sysHandleLog.stream().map(SysHandleLog::getShloId).collect(Collectors.toList()));
        return ResponseResult.success();
    }
}
