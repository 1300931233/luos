package com.luos.console.system.controller;


import com.luos.common.excel.ExportExcelUtil;
import com.luos.common.layui.LayuiTableResult;
import com.luos.common.log.OperationLogAnnotate;
import com.luos.common.shiro.ShiroUtils;
import com.luos.common.utils.CommonUtil;
import com.luos.common.utils.FileUtil;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysManager;
import com.luos.console.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 用户信息表 前端控制器
 */
@Api(tags = "后台管理端--用户管理")
@Controller
@RequestMapping("/sys/sys-manager")
public class SysManagerController {

    @Autowired
    private SysManagerService sysManagerService;
    @Autowired
    private SysRoleInfoService sysRoleInfoService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDictDetailService sysDictDetailService;
    @Autowired
    private SysParamConfService sysParamConfService;

    @Value("${sysconfig.file-root-dir}")
    private String uploadFilePath;

    @Autowired
    private FileUtil fileUtil;

    @ApiOperation(value = "请求用户管理列表页")
    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("sex", sysDictDetailService.listSysDictDetailByDictCode("user_sex"));
        model.addAttribute("status", sysDictDetailService.listSysDictDetailByDictCode("user_status"));
        model.addAttribute("sysParamConf", sysParamConfService.saveGetSysParamConf());
        return "system/manager/list";
    }

    @ApiOperation(value = "请求用户管理新增页")
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("sex", sysDictDetailService.listSysDictDetailByDictCode("user_sex"));
        return "system/manager/add";
    }

    @ApiOperation(value = "请求用户管理编辑页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysManagerService.getById(id));
        model.addAttribute("sex", sysDictDetailService.listSysDictDetailByDictCode("user_sex"));
        return "system/manager/update";
    }

    @ApiOperation(value = "请求用户个人信息页")
    @GetMapping("info")
    public String info(Model model) {
        model.addAttribute("data", sysManagerService.getById(ShiroUtils.getSysUserId()));
        model.addAttribute("sex", sysDictDetailService.listSysDictDetailByDictCode("user_sex"));
        return "system/manager/info";
    }

    @ApiOperation(value = "请求修改密码页")
    @GetMapping("updatePassword")
    public String updatePassword() {
        return "system/manager/password";
    }

    @ApiOperation(value = "请求用户管理授权页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    @GetMapping("auth/{id}")
    public String auth(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysManagerService.getById(id));
        return "system/manager/auth";
    }

    @ApiOperation(value = "根据用户id查询用户角色")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    @GetMapping("listRoleForSelect/{id}")
    @ResponseBody
    public ResponseResult listRoleForSelect(@PathVariable Integer id) {
        return sysRoleInfoService.listXmSelectPojo(id);
    }

    @ApiOperation(value = "分页查询用户信息数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "name", value = "用户姓名"),
            @ApiImplicitParam(name = "account", value = "账户"),
            @ApiImplicitParam(name = "phone", value = "电话"),
            @ApiImplicitParam(name = "sex", value = "性别"),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）")
    })
    @PostMapping("list")
    @ResponseBody
    public LayuiTableResult list(Integer page, Integer limit, SysManager sysManager) {
        return sysManagerService.list(page, limit, sysManager);
    }

    @ApiOperation(value = "添加用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户", required = true),
            @ApiImplicitParam(name = "name", value = "用户名称", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像地址"),
            @ApiImplicitParam(name = "phone", value = "电话", required = true),
            @ApiImplicitParam(name = "sex", value = "性别")
    })
    @PostMapping("add")
    @ResponseBody
    @OperationLogAnnotate(shloModem="添加用户信息",shloRequest="添加用户信息")
    public ResponseResult add(@RequestBody SysManager sysManager) {
        return sysManagerService.add(sysManager);
    }

    @ApiOperation(value = "编辑用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "name", value = "用户名称", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像地址"),
            @ApiImplicitParam(name = "phone", value = "电话", required = true),
            @ApiImplicitParam(name = "sex", value = "性别")
    })
    @PostMapping("update")
    @ResponseBody
    @OperationLogAnnotate(shloModem="编辑用户信息",shloRequest="编辑用户信息")
    public ResponseResult update(@RequestBody SysManager sysManager) {
        return sysManagerService.update(sysManager);
    }

    @ApiOperation(value = "用户授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "roles", value = "多个角色id，逗号分隔", required = true)
    })
    @GetMapping("auth/{id}/{roles}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="用户授权",shloRequest="用户授权")
    public ResponseResult auth(@PathVariable("id") Integer id, @PathVariable("roles") String roles) {
        return sysUserRoleService.auth(id, roles);
    }

    @ApiOperation(value = "禁/启用用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）", required = true)
    })
    @GetMapping("updateStatus/{id}/{status}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="禁/启用用户信息",shloRequest="禁/启用用户信息")
    public ResponseResult updateStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        return sysManagerService.updateStatus(id, status);
    }

    @ApiOperation(value = "删除用户信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    @GetMapping("delete/{id}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="删除用户信息",shloRequest="删除用户信息")
    public ResponseResult delete(@PathVariable Long id) {
        return sysManagerService.delete(id);
    }

    @ApiOperation(value = "编辑个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "name", value = "用户名称", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像地址"),
            @ApiImplicitParam(name = "phone", value = "电话", required = true),
            @ApiImplicitParam(name = "sex", value = "性别")
    })
    @PostMapping("info")
    @ResponseBody
    @OperationLogAnnotate(shloModem="编辑个人信息",shloRequest="编辑个人信息")
    public ResponseResult info(@RequestBody SysManager sysManager) {
        return sysManagerService.info(sysManager);
    }

    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true),
            @ApiImplicitParam(name = "password", value = "新密码", required = true)
    })
    @PostMapping("updatePassword/{oldPassword}/{password}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="修改密码",shloRequest="修改密码")
    public ResponseResult updatePassword(@PathVariable String oldPassword, @PathVariable String password) {
        return sysManagerService.updatePassword(oldPassword.trim(), password.trim());
    }

    @ApiOperation(value = "修改密码")
    @GetMapping("updatepassword/{id}/{password}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="修改密码",shloRequest="修改密码")
    public ResponseResult updatepassword(@PathVariable("id") Integer id, @PathVariable("password") String password) {
        return sysManagerService.updatePassword(id, password);
    }

    @ApiOperation(value = "导出用户信息")
    @GetMapping(value = "/export")
    @OperationLogAnnotate(shloModem="导出管理员信息")
    public void exportXls(SysManager sysManager, HttpServletResponse response) {
        List<SysManager> list = sysManagerService.list(1, Integer.MAX_VALUE, sysManager).getData();
        ExportExcelUtil.exportExcel(list, SysManager.class, "管理员信息表", "管理员信息统计", response);
    }

    @ApiOperation("头像上传")
    @ResponseBody
    @PostMapping(value = "/updateCover", headers = "content-type=multipart/form-data")
    public ResponseResult richTextCover(@RequestParam(name = "file") MultipartFile file) throws IOException {
        cn.hutool.core.lang.Assert.isTrue(file != null && !file.isEmpty(), "上传文件不能为空!");
        // 封面输出资源目录中
        String basePath = uploadFilePath + fileUtil.createFolder();
        File f = new File(basePath,file.getOriginalFilename());
        f.mkdirs();
        file.transferTo(f);
        // 在新的访问路径上追加时间戳，避免浏览器缓存导致图片不变
        return ResponseResult.success("上传成功","file/access/"+ CommonUtil.encode(f.getAbsolutePath()));

    }
}
