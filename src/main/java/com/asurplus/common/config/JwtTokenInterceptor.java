package com.asurplus.common.config;

import com.asurplus.common.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtTokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 根据我们实际需求来判断
         * 1、如果你需要token验证的接口不多，可以使用注解
         * 2、如果你需要token验证的接口很多，每个方法上面都写注解，这样比较麻烦，可以使用路径拦截的方式
         */
        /*JwtToken annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(JwtToken.class);
        } else {
            return true;
        }
        if (annotation == null) {
            return true;
        }*/
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (jwtUtil.verity()) {
            return true;
        }

        //验证失败返回接口信息
//        jwtUtil.requestBack(401,"用户信息已过期，请重新登录");
        return false;
    }

}