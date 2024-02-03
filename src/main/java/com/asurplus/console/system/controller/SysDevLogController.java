package com.asurplus.console.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前端控制器
 **/
@Api(tags = "后台管理端--开发日志")
@Controller
@RequestMapping("/sys/sys-dev-log")
public class SysDevLogController {

    @ApiOperation(value = "请求开发日志列表页")
    @GetMapping("init")
    public String init() {
        return "system/devlog/list";
    }
}
