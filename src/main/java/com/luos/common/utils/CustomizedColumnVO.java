package com.luos.common.utils;

import lombok.Data;

/**
 * 动态生成实体类 bean
 */
@Data
public class CustomizedColumnVO {
    // 列名
    private String property;
    // 属性名
    private String name;
}
