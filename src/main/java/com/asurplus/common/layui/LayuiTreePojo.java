package com.asurplus.common.layui;

import lombok.Data;

import java.util.List;

/**
 * @ClassName LayuiTreePojo
 * layui tree 组件数据格式
 **/
@Data
public class LayuiTreePojo {

    /**
     * 父级id
     */
    private Integer pid;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 名称
     */
    private String title;
    /**
     * 子集
     */
    private List<LayuiTreePojo> children;
    /**
     * 节点是否初始为选中状态
     */
    private Boolean checked = false;
    /**
     * 是节点是否为禁用状态
     */
    private Boolean disabled = false;
    /**
     * 节点是否初始展开
     */
    private boolean spread = true;
    /**
     * 链接
     */
    private String href;
    /**
     * 类型
     */
    private Integer type;
}
