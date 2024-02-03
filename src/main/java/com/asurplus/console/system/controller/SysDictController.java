package com.asurplus.console.system.controller;


import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.log.OperationLogAnnotate;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysDict;
import com.asurplus.console.system.service.SysDictDetailService;
import com.asurplus.console.system.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 前端控制器
 */
@Api(tags = "后台管理端--字典管理")
@Controller
@RequestMapping("/sys/sys-dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "请求字典管理列表页")
    @GetMapping("init")
    @OperationLogAnnotate(shloModem="字典管理",shloRequest="请求字典管理列表页")
    public String init(Model model) {
        model.addAttribute("status", sysDictDetailService.listSysDictDetailByDictCode("dict_status"));
        return "system/dict/list";
    }

    @ApiOperation(value = "请求字典管理新增页")
    @GetMapping("add")
    public String add() {
        return "system/dict/add";
    }

    @ApiOperation(value = "请求字典管理编辑页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "字典管理id", required = true))
    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysDictService.getById(id));
        return "system/dict/update";
    }

    @ApiOperation(value = "分页查询字典管理数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "sdicCode", value = "字典编码"),
            @ApiImplicitParam(name = "sdicName", value = "字典名称"),
            @ApiImplicitParam(name = "sdicStatus", value = "数据状态（0正常，1禁用）")
    })
    @PostMapping("list")
    @ResponseBody
    public LayuiTableResult list(Integer page, Integer limit, SysDict sysDict) {
        return sysDictService.list(page, limit, sysDict);
    }

    @ApiOperation(value = "新增字典管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sdicCode", value = "字典编码", required = true),
            @ApiImplicitParam(name = "sdicName", value = "字典名称", required = true),
            @ApiImplicitParam(name = "sdicDescript", value = "字典描述")
    })
    @PostMapping("add")
    @ResponseBody
    @OperationLogAnnotate(shloModem="新增字典管理",shloRequest="新增字典管理")
    public ResponseResult add(@RequestBody SysDict sysDict) {
        return sysDictService.add(sysDict);
    }

    @ApiOperation(value = "修改字典管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sdicId", value = "字典id", required = true),
            @ApiImplicitParam(name = "sdicCode", value = "字典编码", required = true),
            @ApiImplicitParam(name = "sdicName", value = "字典名称", required = true),
            @ApiImplicitParam(name = "sdicDescript", value = "字典描述")
    })
    @PostMapping("update")
    @ResponseBody
    @OperationLogAnnotate(shloModem="修改字典管理",shloRequest="修改字典管理")
    public ResponseResult update(@RequestBody SysDict sysDict) {
        return sysDictService.update(sysDict);
    }

    @ApiOperation(value = "禁/启用字典管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sdicId", value = "字典id", required = true),
            @ApiImplicitParam(name = "sdicStatus", value = "数据状态（0正常，1禁用）", required = true)
    })
    @GetMapping("updateStatus/{id}/{status}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="禁/启用字典管理",shloRequest="禁/启用字典管理")
    public ResponseResult updateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
        return sysDictService.updateStatus(id, status);
    }

    @ApiOperation(value = "删除字典管理")
    @ApiImplicitParams(@ApiImplicitParam(name = "sdicId", value = "字典id", required = true))
    @GetMapping("delete/{id}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="删除字典管理",shloRequest="删除字典管理")
    public ResponseResult delete(@PathVariable Integer id) {
        return sysDictService.delete(id);
    }
}
