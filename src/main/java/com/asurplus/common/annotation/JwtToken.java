package com.asurplus.common.annotation;

import java.lang.annotation.*;

/**
 * JWT
 * 自定义注解 验证 token
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JwtToken {

}
