package com.luos.console.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前端控制器
 **/
@Api(tags = "后台管理端--图标列表")
@Controller
@RequestMapping("/sys/sys-icon")
public class SysIconControlller {

    @ApiOperation(value = "请求图标列表页")
    @GetMapping("init")
    public String init(){
        return "system/icon/list";
    }
}
