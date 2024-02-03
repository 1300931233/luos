package com.asurplus.console.system.controller;

import com.asurplus.common.utils.ServletUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 前端控制器
 **/
@Api(tags = "后台管理端--异常处理")
@Controller
public class SysExceptionController {

    @ApiOperation(value = "请求400页面")
    @GetMapping("400")
    public String badRequest() {
        return "system/exception/400";
    }

    @ApiOperation(value = "请求401页面")
    @GetMapping("403")
    public String unauthorized() {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            return ServletUtils.redirectTo("login");
        }
        return "system/exception/403";
    }

    @ApiOperation(value = "请求404页面")
    @GetMapping("404")
    public String notFound() {
        return "system/exception/404";
    }

    @ApiOperation(value = "请求500页面")
    @GetMapping("500")
    public String serverError() {
        return "system/exception/500";
    }
}
