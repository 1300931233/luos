package com.asurplus.console.system.controller;


import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysConfigure;
import com.asurplus.console.system.service.SysConfigureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 系统设置 前端控制器
 */
@Controller
@RequestMapping("/sys/sys-configure")
public class SysConfigureController {

    @Autowired
    private SysConfigureService sysConfigureService;

    @ApiOperation("请求系统设置页面")
    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("data", sysConfigureService.saveSysConfigure());
        return "system/sysconfigure/info";
    }

    @ApiOperation("修改系统设置")
    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(@RequestBody SysConfigure sysConfigure) {
        return sysConfigureService.update(sysConfigure);
    }
}
