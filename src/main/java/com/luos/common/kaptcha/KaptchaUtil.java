package com.luos.common.kaptcha;

import com.google.code.kaptcha.Constants;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Kaptcha工具类
 **/
public class KaptchaUtil {

    /**
     * 验证码校验
     *
     * @param
     * @return 正确:true/错误:false
     */
    public static boolean validate(String registerCode, HttpServletRequest request) {
        if (StringUtils.isEmpty(registerCode)) {
            return false;
        }
        // 获取Session中验证码
        Object captcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (null == captcha) {
            return false;
        }
        boolean result = registerCode.equalsIgnoreCase(captcha.toString());
        if (result) {
            request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        }
        return result;
    }
}
