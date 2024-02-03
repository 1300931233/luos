package com.luos.console.system.pojo;

import lombok.Data;

/**
 * XmSelect插件需要数据格式
 */
@Data
public class XmSelectPojo {

    private Long value;
    private String name;
    // 是否选中
    private Boolean selected = true;
}
