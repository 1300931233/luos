package com.asurplus.common.log;

import java.lang.annotation.*;

/**
 * @author nianyu
 * 自定义操作日志注解(用于操作日志存入数据库操作日志表)
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLogAnnotate {

    /**
     *操作模块
     */
    String shloModem() default "";

    /**
     * 操作请求
     */
    String shloRequest() default "";




}
