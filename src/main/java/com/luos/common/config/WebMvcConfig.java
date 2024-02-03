package com.luos.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtTokenInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截规则
        InterceptorRegistration ir = registry.addInterceptor(authorizationInterceptor);
        List<String> pathList = new ArrayList<>();
        pathList.add("/api/**");
        // 拦截路径，开放api请求的路径都拦截
//        ir.addPathPatterns("/api/**");
        ir.addPathPatterns(pathList);
        // 不拦截路径，如：注册、登录、忘记密码等
        ir.excludePathPatterns(
                "/api/CallingCard/**",
                "/api/userInfo/login"
        );
    }
}

