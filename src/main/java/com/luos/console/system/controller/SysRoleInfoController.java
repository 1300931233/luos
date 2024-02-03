package com.luos.console.system.controller;



import com.luos.common.layui.LayuiTableResult;
import com.luos.common.log.OperationLogAnnotate;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysRole;
import com.luos.console.system.service.SysDictDetailService;
import com.luos.console.system.service.SysPermissionInfoService;
import com.luos.console.system.service.SysRoleInfoService;
import com.luos.console.system.service.SysRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 角色信息表 前端控制器
 */
@Api(tags = "后台管理端--角色管理")
@Controller
@RequestMapping("/sys/sys-role-info")
public class SysRoleInfoController {

    @Autowired
    private SysRoleInfoService sysRoleInfoService;
    @Autowired
    private SysPermissionInfoService sysPermissionInfoService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "请求角色管理列表页")
    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("status", sysDictDetailService.listSysDictDetailByDictCode("role_status"));
        return "system/roleinfo/list";
    }

    @ApiOperation(value = "请求角色管理新增页")
    @GetMapping("add")
    public String add() {
        return "system/roleinfo/add";
    }

    @ApiOperation(value = "请求角色管理编辑页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "角色id", required = true))
    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysRoleInfoService.getById(id));
        return "system/roleinfo/update";
    }

    @ApiOperation(value = "请求角色管理授权页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "角色id", required = true))
    @GetMapping("auth/{id}")
    public String auth(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysRoleInfoService.getById(id));
        return "system/roleinfo/auth";
    }

    @ApiOperation(value = "分页查询角色信息数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "name", value = "角色姓名"),
            @ApiImplicitParam(name = "sign", value = "标识"),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）")
    })
    @PostMapping("list")
    @ResponseBody
    public LayuiTableResult list(Integer page, Integer limit, SysRole sysRole) {
        return sysRoleInfoService.list(page, limit, sysRole);
    }

    @ApiOperation(value = "新增角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "sign", value = "标识", required = true),
            @ApiImplicitParam(name = "descript", value = "描述")
    })
    @PostMapping("add")
    @ResponseBody
    @OperationLogAnnotate(shloModem="新增角色信息",shloRequest="新增角色信息")
    public ResponseResult add(@RequestBody SysRole sysRole) {
        return sysRoleInfoService.add(sysRole);
    }

    @ApiOperation(value = "编辑角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "sign", value = "标识", required = true),
            @ApiImplicitParam(name = "descript", value = "描述")
    })
    @PostMapping("update")
    @ResponseBody
    @OperationLogAnnotate(shloModem="编辑角色信息",shloRequest="编辑角色信息")
    public ResponseResult update(@RequestBody SysRole sysRole) {
        return sysRoleInfoService.update(sysRole);
    }

    @ApiOperation(value = "角色授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
            @ApiImplicitParam(name = "roles", value = "多个权限id，逗号分隔", required = true)
    })
    @GetMapping("auth/{id}/{perms}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="角色授权",shloRequest="角色授权")
    public ResponseResult auth(@PathVariable("id") Integer id, @PathVariable("perms") String perms) {
        return sysRolePermissionService.auth(id, perms);
    }

    @ApiOperation(value = "禁/启用角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）", required = true)
    })
    @GetMapping("updateStatus/{id}/{status}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="禁/启用角色信息",shloRequest="禁/启用角色信息")
    public ResponseResult updateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
        return sysRoleInfoService.updateStatus(id, status);
    }

    @ApiOperation(value = "删除角色信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "角色id", required = true))
    @GetMapping("delete/{id}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="删除角色信息",shloRequest="删除角色信息")
    public ResponseResult delete(@PathVariable Integer id) {
        return sysRoleInfoService.delete(id);
    }

    @ApiOperation(value = "根据角色id查询菜单权限树")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "角色id", required = true))
    @GetMapping("listPermissionForTree/{id}")
    @ResponseBody
    public ResponseResult listPermissionForTree(@PathVariable Integer id) {
        return sysPermissionInfoService.listPermissionForTree(id);
    }
}
