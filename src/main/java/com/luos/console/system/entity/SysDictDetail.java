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
import java.util.Date;

/**
 * 数据字典详情实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_DICT_DETAIL")
@ApiModel(value="SysDictDetail对象", description="数据字典明细数据对象")
public class SysDictDetail extends Model<SysDictDetail> {


    @ApiModelProperty(value = "主键id")
    @TableId(value = "SDDE_ID", type = IdType.AUTO)
    private Integer sddeId;

    @ApiModelProperty(value = "数据字典编码")
    @TableField("SDDE_DICT_CODE")
    private String sddeDictCode;

    @ApiModelProperty(value = "编号")
    @TableField("SDDE_CODE")
    private String sddeCode;

    @ApiModelProperty(value = "名称")
    @TableField("SDDE_NAME")
    private String sddeName;

    @ApiModelProperty(value = "创建人")
    @TableField("SDDE_CREATEUSER_ID")
    private Integer sddeCreateUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField("SDDE_CREATEUSER_TIME")
    private Date sddeCreateTime;

    @ApiModelProperty(value = "修改人")
    @TableField("SDDE_UPDATEUSER_ID")
    private Integer sddeUpdateUserId;

    @ApiModelProperty(value = "修改时间")
    @TableField("SDDE_UPDATEUSER_TIME")
    private Date sddeUpdateTime;


    @Override
    protected Serializable pkVal() {
        return this.sddeId;
    }

}
