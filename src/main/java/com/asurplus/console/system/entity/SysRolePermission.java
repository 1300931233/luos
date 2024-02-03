package com.asurplus.console.system.entity;

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

/**
 * 角色-权限关系表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ROLE_PERMISSION")
@ApiModel(value="SysRolePermission对象", description="角色-权限关系表")
public class SysRolePermission extends Model<SysRolePermission> {


    @ApiModelProperty(value = "角色权限id")
    @TableId(value = "SRPE_ID", type = IdType.AUTO)
    private Integer srpeId;

    @ApiModelProperty(value = "角色id")
    @TableField("SRPE_ROLE_ID")
    private Integer srpeRoleId;

    @ApiModelProperty(value = "权限id")
    @TableField("SRPE_PERMISSION_ID")
    private Integer srpePermissionId;


    @Override
    protected Serializable pkVal() {
        return this.srpeId;
    }

}
