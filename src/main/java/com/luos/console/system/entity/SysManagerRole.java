package com.luos.console.system.entity;

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
 * 管理员-角色关系表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_MANAGER_ROLE")
@ApiModel(value="SysManagerRole对象", description="管理员-角色关系表")
public class SysManagerRole extends Model<SysManagerRole> {


    @ApiModelProperty(value = "管理员角色id")
    @TableId(value = "SMRO_ID", type = IdType.AUTO)
    private Integer smroId;

    @ApiModelProperty(value = "管理员id")
    @TableField("SMRO_MANAGER_ID")
    private Integer smroManagerId;

    @ApiModelProperty(value = "角色id")
    @TableField("SMRO_ROLE_ID")
    private Integer smroRoleId;


    @Override
    protected Serializable pkVal() {
        return this.smroId;
    }

}
