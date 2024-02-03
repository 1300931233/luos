package com.luos.common.kaptcha;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.luos.common.utils.RandomUtils;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysParamConf;
import com.luos.console.system.service.SysParamConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * kaptcha调用
 **/
@Api(tags = "获取验证码")
@Slf4j
@Controller
@RequestMapping("kaptcha")
public class KaptchaController {

    @Autowired
    private Producer producer;
    @Autowired
    private SysParamConfService sysParamConfService;
    // 验证码
    private String capText;

    @ApiOperation(value = "获取验证码")
    @GetMapping("kaptcha-image")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        // String capText = producer.createText();
        // 系统参数
        SysParamConf sysParamConf = sysParamConfService.saveGetSysParamConf();
        // 验证码
//        String capText;
        // 纯数字
        if (0 == sysParamConf.getSpcoLoginCodeType()) {
            capText = RandomUtils.number(sysParamConf.getSpcoLoginCodeLen());
        }
        // 纯字母
        else if (1 == sysParamConf.getSpcoLoginCodeType()) {
            capText = RandomUtils.getString(sysParamConf.getSpcoLoginCodeLen());
        }
        // 数字 + 字母
        else if (2 == sysParamConf.getSpcoLoginCodeType()) {
            capText = RandomUtils.getMixString(sysParamConf.getSpcoLoginCodeLen());
        }
        // 否则纯数字
        else {
            capText = RandomUtils.number(sysParamConf.getSpcoLoginCodeLen());
        }
        log.info("******************当前验证码为：{}******************", capText);

        // store the text in the session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        // create the image with the text
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @ApiOperation(value = "获取验证码字符串")
    @GetMapping("kaptcha-capText")
    @ResponseBody
    public ResponseResult getKaptchaCapText(){
        return ResponseResult.success(capText);
    }
}
