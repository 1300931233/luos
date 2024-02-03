package com.luos.console.system.pojo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName MenuPojo
 * 返回前端的菜单数据
 **/
@Data
public class MenuPojo {

    /**
     * 父级id
     */
    private Integer pid;
    /**
     * id
     */
    private Integer id;
    /**
     * 类型（0--目录1--菜单2--按钮）
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 链接地址
     */
    private String href;
    /**
     * 链接方式（内链--_self，外链--_blank）
     */
    private String target = "_self";
    /**
     * 子集
     */
    private List<MenuPojo> child;
}
