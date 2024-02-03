package com.asurplus.console.system.controller;


import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.log.OperationLogAnnotate;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysDictDetail;
import com.asurplus.console.system.service.SysDictDetailService;
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
15
 */
@Api(tags = "后台管理端--字典配置")
@Controller
@RequestMapping("/sys/sys-dict-detail")
public class SysDictDetailController {

    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "请求字典配置列表页")
    @ApiImplicitParams(@ApiImplicitParam(name = "dictCode", value = "字典类型", required = true))
    @GetMapping("init/{dictCode}")
    @OperationLogAnnotate(shloModem="字典配置",shloRequest="请求字典配置列表页")
    public String auth(@PathVariable String dictCode, Model model) {
        model.addAttribute("dictCode", dictCode);
        return "system/dictdetail/list";
    }

    @ApiOperation(value = "请求字典配置新增页")
    @ApiImplicitParams(@ApiImplicitParam(name = "dictCode", value = "字典类型code", required = true))
    @GetMapping("add/{dictCode}")
    public String add(@PathVariable String dictCode, Model model) {
        model.addAttribute("sddeDictCode", dictCode);
        return "system/dictdetail/add";
    }

    @ApiOperation(value = "请求字典配置编辑页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "字典配置id", required = true))
    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("data", sysDictDetailService.getById(id));
        return "system/dictdetail/update";
    }

    @ApiOperation(value = "查询字典配置列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "dictCode", value = "字典类型code", required = true))
    @PostMapping("listDetail")
    @ResponseBody
    public LayuiTableResult listDetail(String dictCode) {
        return sysDictDetailService.list(dictCode);
    }

    @ApiOperation(value = "新增字典配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictCode", value = "字典类型code", required = true),
            @ApiImplicitParam(name = "code", value = "字典配置code", required = true),
            @ApiImplicitParam(name = "name", value = "字典配置名称", required = true)
    })
    @PostMapping("add")
    @ResponseBody
    @OperationLogAnnotate(shloModem="新增字典配置",shloRequest="新增字典配置")
    public ResponseResult add(@RequestBody SysDictDetail sysDictDetail) {
        return sysDictDetailService.add(sysDictDetail);
    }

    @ApiOperation(value = "编辑字典配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictCode", value = "字典类型code", required = true),
            @ApiImplicitParam(name = "id", value = "字典配置id", required = true),
            @ApiImplicitParam(name = "code", value = "字典配置code", required = true),
            @ApiImplicitParam(name = "name", value = "字典配置名称", required = true)
    })
    @PostMapping("update")
    @ResponseBody
    @OperationLogAnnotate(shloModem="编辑字典配置",shloRequest="编辑字典配置")
    public ResponseResult update(@RequestBody SysDictDetail sysDictDetail) {
        return sysDictDetailService.update(sysDictDetail);
    }

    @ApiOperation(value = "删除字典配置")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "字典配置id", required = true))
    @GetMapping("delete/{id}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="删除字典配置",shloRequest="删除字典配置")
    public ResponseResult delete(@PathVariable Long id) {
        return sysDictDetailService.delete(id);
    }
}