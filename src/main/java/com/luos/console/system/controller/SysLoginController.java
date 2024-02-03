package com.luos.console.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.luos.common.consts.SystemConst;
import com.luos.common.enums.StatusEnums;
import com.luos.common.kaptcha.KaptchaUtil;
import com.luos.common.log.OperationLogAnnotate;
import com.luos.common.shiro.ShiroUtils;
import com.luos.common.utils.PasswordUtils;
import com.luos.common.utils.ResponseResult;
import com.luos.common.utils.ServletUtils;
import com.luos.console.system.entity.SysManager;
import com.luos.console.system.entity.SysParamConf;
import com.luos.console.system.service.SysConfigureService;
import com.luos.console.system.service.SysLoginLogService;
import com.luos.console.system.service.SysParamConfService;
import com.luos.console.system.service.SysPermissionInfoService;
import com.luos.console.system.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 前端控制器
 **/
@Api(tags = "后台管理端--登录")
@Controller
public class SysLoginController {

    @Autowired
    private SysPermissionInfoService sysPermissionInfoService;
    @Autowired
    private SysLoginLogService sysLoginLogService;
    @Autowired
    private SysConfigureService sysConfigureService;
    @Autowired
    private SysParamConfService sysParamConfService;

    @ApiOperation(value = "请求登录页面")
    @GetMapping("login")
    public String init(Model model) {
        model.addAttribute("sysConfig", sysConfigureService.saveSysConfigure());
        return "system/login";
    }

    @ApiOperation(value = "请求欢迎页面")
    @GetMapping("welcome")
    public String welcome(Model model) {
        model.addAttribute("sysConfig", sysConfigureService.saveSysConfigure());
        return "system/welcome";
    }

    @ApiOperation(value = "请求主页面")
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("data", ShiroUtils.getSysUserInfo());
        model.addAttribute("sysConfig", sysConfigureService.saveSysConfigure());
        // 判断用户是否更改密码
        model.addAttribute("defaultPassword", 0);
        SysParamConf sysParamConf = sysParamConfService.saveGetSysParamConf();
        if (null != sysParamConf && StringUtils.isNotBlank(sysParamConf.getSpcoDefaultPassword())) {
            SysManager sysManager = ShiroUtils.getSysUserInfo();
            if (sysManager.getSmanPassword().equals(PasswordUtils.getPassword(sysParamConf.getSpcoDefaultPassword(), sysManager.getSmanAccount()))) {
                model.addAttribute("defaultPassword", 1);
            }
        }
        return "system/index";
    }

    @ApiOperation(value = "登录验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "resCode", value = "验证码", required = true),
            @ApiImplicitParam(name = "rememberMe", value = "记住登录", required = true)
    })
    @PostMapping("doLogin")
    @ResponseBody
    public ResponseResult doLogin(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        // 验证码
        if (!KaptchaUtil.validate(loginVO.getResCode(), request)) {
            return ResponseResult.error(StatusEnums.KAPTCH_ERROR);
        }
        try {
           /* // 验证帐号和密码
            UsernamePasswordToken token = new UsernamePasswordToken(loginVO.getAccount(), loginVO.getPassword());
            // 记住登录状态
            token.setRememberMe(loginVO.getRememberMe());
            // 执行登录
            SecurityUtils.getSubject().login(token);*/

            //获取当前的用户
            Subject subject = SecurityUtils.getSubject();
            // 验证帐号和密码，记住登录
            UsernamePasswordToken token = new UsernamePasswordToken(loginVO.getAccount(), loginVO.getPassword(), loginVO.getRememberMe());
            // 执行登录
            subject.login(token);
            // 将用户保存到session中
            request.getSession().setAttribute(SystemConst.SYSTEM_USER_SESSION, ShiroUtils.getSysUserInfo());
            // 保存登录日志
            sysLoginLogService.save(loginVO.getAccount(), 0, "用户登录成功");
            return ResponseResult.success("登录成功，欢迎回来！");
        } catch (UnknownAccountException e) {
            sysLoginLogService.save(loginVO.getAccount(), 1, "登录账户不存在");
            return ResponseResult.error("账户不存在");
        } catch (DisabledAccountException e) {
            sysLoginLogService.save(loginVO.getAccount(), 1, "登录账户已被冻结");
            return ResponseResult.error("账户已被冻结");
        } catch (IncorrectCredentialsException e) {
            sysLoginLogService.save(loginVO.getAccount(), 1, "登录密码不正确");
            return ResponseResult.error("密码不正确");
        } catch (ExcessiveAttemptsException e) {
            sysLoginLogService.save(loginVO.getAccount(), 1, "密码连续输入错误超过5次，锁定半小时");
            return ResponseResult.error("密码连续输入错误超过5次账号已被锁定,请30分钟后重试!");
        } catch (RuntimeException e) {
            sysLoginLogService.save(loginVO.getAccount(), 1, "未知错误");
            return ResponseResult.error("未知错误");
        }
    }

    @ApiOperation(value = "登录成功，跳转主页面")
    @PostMapping("success")
    public String success() {
        return ServletUtils.redirectTo("/");
    }

    @ApiOperation(value = "初始化菜单数据")
    @GetMapping("initMenu")
    @ResponseBody
    public JSONObject initMenu(HttpSession session) {
        Object object = session.getAttribute(SystemConst.SYSTEM_USER_MENU);
        if (null != object) {
            return (JSONObject) object;
        }
        return sysPermissionInfoService.initMenu(session);
    }

    @ApiOperation(value = "退出登录")
    @GetMapping(value = "loginOut")
    @OperationLogAnnotate(shloModem="退出登录",shloRequest="退出登录")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return ServletUtils.redirectTo("login");
    }

    @ApiOperation(value = "页面解锁")
    @ApiImplicitParams(@ApiImplicitParam(name = "password", value = "登录密码", required = true))
    @PostMapping(value = "unlock")
    @ResponseBody
    @OperationLogAnnotate(shloModem="页面解锁",shloRequest="页面解锁")
    public ResponseResult unlock(@RequestBody JSONObject param) {
        if (null == param) {
            return ResponseResult.error("请输入密码");
        }
        String password = param.getString("password");
        if (StringUtils.isBlank(password)) {
            return ResponseResult.error("请输入密码");
        }
        SysManager sysManager = ShiroUtils.getSysUserInfo();
        if (null == sysManager) {
            return ResponseResult.error("用户信息异常");
        }
        if (!PasswordUtils.getPassword(password, sysManager.getSmanAccount()).equals(sysManager.getSmanPassword())) {
            return ResponseResult.error("密码错误");
        }
        return ResponseResult.success();
    }
}
