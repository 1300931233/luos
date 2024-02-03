package com.luos.console.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前端控制器
 **/
@Api(tags = "后台管理端--组件专区")
@Controller
@RequestMapping("/components")
public class ComponentsController {

    @ApiOperation(value = "请求条形码页")
    @GetMapping("barcode/init")
    public String barcode() {
        return "components/barcode";
    }

    @ApiOperation(value = "请求数字步进器页")
    @GetMapping("countUp/init")
    public String countUp() {
        return "components/countUp";
    }

    @ApiOperation(value = "请求消息通知页")
    @GetMapping("layNotify/init")
    public String layNotify() {
        return "components/layNotify";
    }

    @ApiOperation(value = "请求加载进度条页")
    @GetMapping("nprogress/init")
    public String nprogress() {
        return "components/nprogress";
    }

    @ApiOperation(value = "请求图形二维码页")
    @GetMapping("qrcode/init")
    public String qrcode() {
        return "components/qrcode";
    }

    @ApiOperation(value = "请求通知公告页")
    @GetMapping("notice/init")
    public String notice() {
        return "components/notice";
    }

    @ApiOperation(value = "请求文件上传页")
    @GetMapping("zyupload/init")
    public String zyupload() {
        return "components/zyupload";
    }

    @ApiOperation(value = "请求裁剪上传页")
    @GetMapping("cropper/init")
    public String cropper() {
        return "components/cropper";
    }
}
