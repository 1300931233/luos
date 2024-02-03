package com.luos.console.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.luos.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ROLE")
@ApiModel(value="SysRoleInfo对象", description="角色信息表")
public class SysRole extends Model<SysRole> {


    @ApiModelProperty(value = "角色ID")
    @TableId(value = "SROL_ID", type = IdType.AUTO)
    private Integer srolId;

    @ApiModelProperty(value = "角色名称")
    @TableField("SROL_NAME")
    private String srolName;

    @ApiModelProperty(value = "角色标识")
    @TableField("SROL_SIGN")
    private String srolSign;

    @ApiModelProperty(value = "角色描述")
    @TableField("SROL_DESCRIPT")
    private String srolDescript;

    @ApiModelProperty(value = "状态（0--正常1--停用）")
    @TableField("SROL_STATUS")
    @Dict(dictCode = "role_status")
    private Integer srolStatus;

    @ApiModelProperty(value = "创建人")
    @TableField("SROL_CREATEUSER_ID")
    private Integer srolCreateUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField("SROL_CREATEUSER_TIME")
    private Date srolCreateTime;

    @ApiModelProperty(value = "修改人")
    @TableField("SROL_UPDATEUSER_ID")
    private Integer srolUpdateUser;

    @ApiModelProperty(value = "修改时间")
    @TableField("SROL_UPDATEUSER_TIME")
    private Date srolUpdateTime;

    @ApiModelProperty(value = "删除状态（0--未删除1--已删除）")
    @TableField("SROL_DEL_FLAG")
//    @TableLogic
    private Integer srolDelFlag;

    @Override
    protected Serializable pkVal() {
        return this.srolId;
    }

}
