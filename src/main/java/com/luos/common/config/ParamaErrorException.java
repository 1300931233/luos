package com.luos.common.config;

/**
 * 自定义参数错误异常处理器
 */
public class ParamaErrorException extends RuntimeException {

    public ParamaErrorException() {
    }

    public ParamaErrorException(String message) {
        super(message);
    }

}