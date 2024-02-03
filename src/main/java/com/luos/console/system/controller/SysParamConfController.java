package com.luos.console.system.controller;


import com.luos.common.log.OperationLogAnnotate;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysParamConf;
import com.luos.console.system.service.SysDictDetailService;
import com.luos.console.system.service.SysParamConfService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 系统参数设置 前端控制器
 */
@Controller
@RequestMapping("/system/sys-param-conf")
public class SysParamConfController {

    @Autowired
    private SysParamConfService sysParamConfService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("data", sysParamConfService.saveGetSysParamConf());
        // 类型
        model.addAttribute("login", sysDictDetailService.listSysDictDetailByDictCode("login_code_type"));
        model.addAttribute("sms", sysDictDetailService.listSysDictDetailByDictCode("sms_code_type"));
        return "system/sysparamconf/info";
    }

    @ApiOperation("编辑系统参数配置")
    @PostMapping("update")
    @ResponseBody
    @OperationLogAnnotate(shloModem="编辑系统参数配置",shloRequest="编辑系统参数配置")
    public ResponseResult update(@RequestBody SysParamConf sysParamConf) {
        return sysParamConfService.updateSysParamConf(sysParamConf);
    }

}
