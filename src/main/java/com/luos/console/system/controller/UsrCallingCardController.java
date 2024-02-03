package com.luos.console.system.controller;


import com.luos.common.layui.LayuiTableResult;
import com.luos.common.log.OperationLogAnnotate;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.UsrCallingCard;
import com.luos.console.system.service.UsrCallingCardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xlp
 * @since 2023-06-07
 */
@Controller
@RequestMapping("/usr/calling-card")
public class UsrCallingCardController {

    @Autowired
    private UsrCallingCardService callingCardService;



    @ApiOperation(value = "请求名片管理列表页")
    @GetMapping("init")
    @OperationLogAnnotate(shloModem="名片管理",shloRequest="请求名片管理列表页")
    public String init(Model model) {
        return "system/usr/callingcard";
    }

    @ApiOperation(value = "请求名片详情页")
    @GetMapping("details/{id}")
    @OperationLogAnnotate(shloModem="名片管理",shloRequest="请求名片详情页")
    public String detail(Model model, @PathVariable Integer id) {
        model.addAttribute("callingCard",callingCardService.getById(id));
        return "system/usr/carddetails";
    }
    @ApiOperation(value = "请求名片详情页")
    @ResponseBody
    @PostMapping("list")
    public LayuiTableResult list(Integer page, Integer limit, UsrCallingCard callingCard) {
        return callingCardService.list(page, limit,callingCard);
    }

    /**
     * 进入用户新增页面
     * @param model
     * @return
     */
    @ApiOperation(value = "请求名片新增页")
    @GetMapping("add")
    @OperationLogAnnotate(shloModem="名片管理", shloRequest="进入名片新增")
    public String add(Model model) {
        return "system/usr/cardadd";
    }

    /**
     * 读者审核保存
     * @param
     */
    @ResponseBody
    @PostMapping("save")
    @OperationLogAnnotate(shloModem="名片管理", shloRequest="进入名片保存")
    public ResponseResult save(@RequestBody UsrCallingCard usrCallingCard ) {

        return ResponseResult.success(callingCardService.save(usrCallingCard));



    }


    /**
     * 上传文件
     * <p>
     * Ajax方式上传文件
     *
     * @return
     */
    /*@ApiOperation("上传文件-Ajax方式1")
    @PostMapping("uploadFileAjax")
    @ResponseBody
    @OperationLogAnnotate(shloModem="上传文件-Ajax方式1",shloRequest="上传文件-Ajax方式1")
    public ResponseResult uploadFileAjax(HttpServletRequest request) {


    }
*/




}
