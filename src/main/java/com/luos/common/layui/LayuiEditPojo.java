package com.luos.common.layui;

import lombok.Data;

/**
 * layui富文本上传回调
 */
@Data
public class LayuiEditPojo {

    /**
     * 0表示成功，其它失败
     */
    private Integer code;
    /**
     * 提示信息，一般上传失败后返回
     */
    private String msg;
    /**
     * 内容
     */
    private LayuiEditItemPojo data;
}
