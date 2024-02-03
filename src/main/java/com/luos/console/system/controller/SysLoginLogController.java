package com.luos.console.system.controller;


import com.luos.common.layui.LayuiTableResult;
import com.luos.common.log.OperationLogAnnotate;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysLoginLog;
import com.luos.console.system.service.SysDictDetailService;
import com.luos.console.system.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 前端控制器
 */
@Api(tags = "后台管理端--登录日志")
@Controller
@RequestMapping("/sys/sys-login-log")
public class SysLoginLogController {

    @Autowired
    private SysLoginLogService sysLoginLogService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "请求登录日志列表页")
    @GetMapping("init")
    @OperationLogAnnotate(shloModem="登录日志",shloRequest="查看登录日志")
    public String init(Model model) {
        model.addAttribute("status", sysDictDetailService.listSysDictDetailByDictCode("login_status"));
        return "system/loginlog/list";
    }

    @ApiOperation(value = "分页查询登录日志数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "account", value = "登录账户"),
            @ApiImplicitParam(name = "status", value = "数据状态（0成功，1失败）")
    })
    @PostMapping("list")
    @ResponseBody
    public LayuiTableResult list(Integer page, Integer limit, SysLoginLog sysLoginLog) {
        return sysLoginLogService.list(page, limit, sysLoginLog);
    }

    @ResponseBody
    @ApiOperation(value = "批量删除登录日志")
    @PostMapping("batchDelete")
    @OperationLogAnnotate(shloModem="批量删除登录日志")
    public ResponseResult batchDelete(@RequestBody List<SysLoginLog> sysLoginLog){
        sysLoginLogService.removeByIds(sysLoginLog.stream().map(SysLoginLog::getSlloId).collect(Collectors.toList()));
        return ResponseResult.success();
    }

}
