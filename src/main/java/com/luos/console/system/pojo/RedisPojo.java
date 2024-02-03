package com.luos.console.system.pojo;

import lombok.Data;

/**
 * @ClassName RedisPojo
 * 页面管理redis对象
 **/
@Data
public class RedisPojo {

    /**
     * 键
     */
    private String key;
    /**
     * 值
     */
    private Object value;
    /**
     * 过期时间
     */
    private Long expire;
}
