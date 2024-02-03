package com.asurplus.console.system.entity;

import com.asurplus.common.annotation.Dict;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单权限表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_PERMISSION_INFO")
@ApiModel(value = "SysPermissionInfo对象", description = "菜单权限信息表")
public class SysPermissionInfo extends Model<SysPermissionInfo> {


    @ApiModelProperty(value = "权限ID")
    @TableId(value = "SPIN_ID", type = IdType.AUTO)
    private Integer spinId;

    @ApiModelProperty(value = "父级ID")
    @TableField("SPIN_PID")
    private Integer spinPid;

    @ApiModelProperty(value = "名称")
    @TableField("SPIN_NAME")
    private String spinName;

    @ApiModelProperty(value = "类型（0--目录1--菜单2--按钮）")
    @TableField("SPIN_TYPE")
    private Integer spinType;

    @ApiModelProperty(value = "标识")
    @TableField("SPIN_SIGN")
    private String spinSign;

    @ApiModelProperty(value = "链接地址")
    @TableField("SPIN_HREF")
    private String spinHref;

    @ApiModelProperty(value = "排序")
    @TableField("SPIN_SORT")
    private Integer spinSort;

    @ApiModelProperty(value = "图标")
    @TableField("SPIN_ICON")
    private String spinIcon;

    @ApiModelProperty(value = "状态（0--正常1--停用）")
    @TableField("SPIN_STATUS")
    @Dict(dictCode = "data_status")
    private Integer spinStatus;

    @ApiModelProperty(value = "打开方式")
    @TableField("SPIN_TARGET")
    private String spinTarget;

    @ApiModelProperty(value = "描述")
    @TableField("SPIN_DESCRIPT")
    private String spinDescript;

    @ApiModelProperty(value = "创建人")
    @TableField("SPIN_CREATEUSER_ID")
    private Integer createUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField("SPIN_CREATEUSER_TIME")
    private Date spinCreateTime;

    @ApiModelProperty(value = "修改人")
    @TableField("SPIN_UPDATEUSER_ID")
    private Integer spinUpdateUserId;

    @ApiModelProperty(value = "修改时间")
    @TableField("SPIN_UPDATEUSER_TIME")
    private Date spinUpdateTime;

    @ApiModelProperty(value = "删除状态（0--未删除1--已删除）")
    @TableField("SPIN_DEL_FLAG")
    // @TableLogic
    private Integer spinDelFlag;

    @ApiModelProperty(value = "是否还有子集")
    @TableField(exist = false)
    private Boolean haveChild=true;


    @Override
    protected Serializable pkVal() {
        return this.spinId;
    }

}
