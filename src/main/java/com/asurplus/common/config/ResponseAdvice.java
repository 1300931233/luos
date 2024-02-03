package com.asurplus.common.config;

/**
 * 开发公司:成都联图科技有限责任公司
 * 版权:本文件版权归属成都联图科技有限责任公司
 *
 * @Author 马义雯
 * @Date 2022年11月01日 14:47
 * @Version 1.0
 * @Descritpion
 **/

import com.asurplus.common.layui.*;
import com.asurplus.common.utils.ResponseResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *  统一返回结果封装
 */
@RestControllerAdvice(basePackages ={"com.asurplus.console", "com.asurplus.api"})
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class clazz = returnType.getParameterType();
        // 自定义返回结果
        if(clazz.equals(ResponseResult.class)){
            return false;
        }
        // layui组件返回
        if(clazz.equals(LayuiEditItemPojo.class) || clazz.equals(LayuiEditPojo.class)
                || clazz.equals(LayuiTableResult.class) || clazz.equals(LayuiTreePojo.class)
                || clazz.equals(LayuiUploadItemPojo.class) || clazz.equals(LayuiUploadPojo.class)
                || clazz.equals(LayuiUploadResult.class)){
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 如果返回值是String类型，那就手动把Result对象转换成JSON字符串
        if (body instanceof String) {
            try {
                // 解决com.example.response.Result cannot be cast to java.lang.String异常
                return new ObjectMapper().writeValueAsString(ResponseResult.success(body));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        if (body instanceof IPage) {
            IPage page = (IPage) body;
            return new LayuiTableResult(page.getTotal(), page.getRecords());
        } else {
            return ResponseResult.success(body);
        }
    }
}
