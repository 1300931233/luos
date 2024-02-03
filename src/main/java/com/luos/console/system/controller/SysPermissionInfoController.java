package com.luos.console.system.controller;


import com.alibaba.fastjson.JSONObject;
import com.luos.common.layui.LayuiTableResult;
import com.luos.common.log.OperationLogAnnotate;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysPermissionInfo;
import com.luos.console.system.service.SysPermissionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单权限表 前端控制器
 */
@Api(tags = "后台管理端--菜单管理")
@Controller
@RequestMapping("/sys/sys-permission-info")
public class SysPermissionInfoController {

    @Autowired
    private SysPermissionInfoService sysPermissionInfoService;

    @ApiOperation(value = "请求权限菜单管理列表页")
    @GetMapping("init")
    public String init() {
        return "system/permissioninfo/list";
    }

    @ApiOperation(value = "请求权限菜单管理新增页")
    @GetMapping("add")
    public String add() {
        return "system/permissioninfo/add";
    }

    @ApiOperation(value = "请求权限菜单管理编辑页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "权限菜单id", required = true))
    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysPermissionInfoService.getById(id));
        return "system/permissioninfo/update";
    }

    @ApiOperation(value = "查询权限菜单列表数据")
    @GetMapping("list")
    @ResponseBody
    public LayuiTableResult list(Integer pid) {
        List<SysPermissionInfo> list = sysPermissionInfoService.list(pid);
        return new LayuiTableResult<>(null != list && !list.isEmpty() ? Long.valueOf(list.size()) : 0, list);
    }

    @ApiOperation(value = "权限菜单数据排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spinId", value = "权限菜单id", required = true),
            @ApiImplicitParam(name = "spinSort", value = "顺序", required = true)
    })
    @GetMapping("updateSort/{id}/{sort}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="权限菜单数据排序",shloRequest="权限菜单数据排序")
    public ResponseResult updateSort(@PathVariable("id") Integer id, @PathVariable("sort") Integer sort) {
        return sysPermissionInfoService.updateSort(id, sort);
    }

    @ApiOperation(value = "根据类型获取权限菜单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spinType", value = "权限菜单类型", required = true),
            @ApiImplicitParam(name = "spinPid", value = "父级菜单id", required = true)
    })
    @GetMapping("listPermissionInfoByPid/{type}/{pid}")
    @ResponseBody
    public ResponseResult listPermissionInfoByPid(@PathVariable("type") Integer type, @PathVariable("pid") Integer pid) {
        return sysPermissionInfoService.listPermissionInfoByPid(type, pid);
    }

    @ApiOperation(value = "新增权限菜单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spinType", value = "类型", required = true),
            @ApiImplicitParam(name = "spinPid", value = "父级id"),
            @ApiImplicitParam(name = "spinName", value = "名称", required = true),
            @ApiImplicitParam(name = "spinSign", value = "标识", required = true),
            @ApiImplicitParam(name = "spinHref", value = "链接地址", required = true),
            @ApiImplicitParam(name = "spinTarget", value = "打开方式", required = true),
            @ApiImplicitParam(name = "spinIcon", value = "图标"),
            @ApiImplicitParam(name = "spinSort", value = "排序"),
            @ApiImplicitParam(name = "spinDescript", value = "描述")
    })
    @PostMapping("add")
    @ResponseBody
    @OperationLogAnnotate(shloModem="新增权限菜单数据",shloRequest="新增权限菜单数据")
    public ResponseResult add(@RequestBody JSONObject jsonObject) {
        return sysPermissionInfoService.add(jsonObject);
    }

    @ApiOperation(value = "编辑权限菜单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spinType", value = "权限菜单id", required = true),
            @ApiImplicitParam(name = "spinName", value = "名称", required = true),
            @ApiImplicitParam(name = "spinSign", value = "标识", required = true),
            @ApiImplicitParam(name = "spinHref", value = "链接地址", required = true),
            @ApiImplicitParam(name = "spinTarget", value = "打开方式", required = true),
            @ApiImplicitParam(name = "spinIcon", value = "图标"),
            @ApiImplicitParam(name = "spinSort", value = "排序"),
            @ApiImplicitParam(name = "spinDescript", value = "描述")
    })
    @PostMapping("update")
    @ResponseBody
    @OperationLogAnnotate(shloModem="编辑权限菜单数据",shloRequest="编辑权限菜单数据")
    public ResponseResult update(@RequestBody SysPermissionInfo sysPermissionInfo) {
        return sysPermissionInfoService.update(sysPermissionInfo);
    }

    @ApiOperation(value = "禁/启用权限菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spinId", value = "权限菜单id", required = true),
            @ApiImplicitParam(name = "spinStatus", value = "数据状态（0正常，1禁用）", required = true)
    })
    @GetMapping("updateStatus/{id}/{status}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="禁/启用权限菜单信息",shloRequest="禁/启用权限菜单信息")
    public ResponseResult updateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
        return sysPermissionInfoService.updateStatus(id, status);
    }

    @ApiOperation(value = "删除权限菜单信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "spinId", value = "权限菜单id", required = true))
    @GetMapping("delete/{id}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="删除权限菜单信息",shloRequest="删除权限菜单信息")
    public ResponseResult delete(@PathVariable Integer id) {
        return sysPermissionInfoService.delete(id);
    }
}
