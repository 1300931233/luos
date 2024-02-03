package com.asurplus.console.system.controller;


import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.log.OperationLogAnnotate;
import com.asurplus.console.system.entity.UsrUserInfo;
import com.asurplus.console.system.service.UsrUserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xlp
 * @since 2023-06-07
 */
@Controller
@RequestMapping("/usr/user-info")
public class UsrUserInfoController {

    @Autowired
    private UsrUserInfoService usrUserInfoService;

    @ApiOperation(value = "请求用户管理列表页")
    @GetMapping("init")
    @OperationLogAnnotate(shloModem="用户管理",shloRequest="请求用户管理列表页")
    public String init(Model model) {
        return "system/usr/userinfo";
    }

    @ResponseBody
    @PostMapping("list")
    public LayuiTableResult list(Integer page, Integer limit, UsrUserInfo userInfo) {
        return usrUserInfoService.list(page, limit,userInfo);
    }
}
