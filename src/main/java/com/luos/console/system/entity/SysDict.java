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
 * 数据字典实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_DICT")
@ApiModel(value="SysDict对象", description="数据字典对象")
public class SysDict extends Model<SysDict> {


    @ApiModelProperty(value = "主键id")
    @TableId(value = "SDIC_ID", type = IdType.AUTO)
    private Integer sdicId;

    @ApiModelProperty(value = "编号")
    @TableField("SDIC_CODE")
    private String sdicCode;

    @ApiModelProperty(value = "名称")
    @TableField("SDIC_NAME")
    private String sdicName;

    @ApiModelProperty(value = "描述")
    @TableField("SDIC_DESCRIPT")
    private String sdicDescript;

    @ApiModelProperty(value = "状态（0--正常1--冻结）")
    @TableField("SDIC_STATUS")
    @Dict(dictCode = "dict_status")
    private Integer sdicStatus;

    @ApiModelProperty(value = "创建人")
    @TableField("SDIC_CREATEUSER_ID")
    private Integer sdicCreateUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField("SDIC_CREATEUSER_TIME")
    private Date sdicCreateTime;

    @ApiModelProperty(value = "修改人")
    @TableField("SDIC_UPDATEUSER_ID")
    private Integer sdicUpdateUserId;

    @ApiModelProperty(value = "修改时间")
    @TableField("SDIC_UPDATEUSER_TIME")
    private Date sdicUpdateTime;

    @ApiModelProperty(value = "删除状态（0，正常，1已删除）")
    @TableField("SDIC_DEL_FLAG")
  //  @TableLogic
    private Integer sdicDelFlag;

    @Override
    protected Serializable pkVal() {
        return this.sdicId;
    }

}
