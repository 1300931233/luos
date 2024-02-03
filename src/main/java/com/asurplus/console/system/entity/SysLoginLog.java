package com.asurplus.console.system.entity;

import com.asurplus.common.annotation.Dict;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_LOGIN_LOG")
@ApiModel(value = "SysLoginLog对象", description = "登录日志")
public class SysLoginLog extends Model<SysLoginLog> {


    @ApiModelProperty(value = "主键id")
    @TableId(value = "SLLO_ID", type = IdType.AUTO)
    private Long slloId;

    @ApiModelProperty(value = "登录账号")
    @TableField("SLLO_ACCOUNT")
    private String slloAccount;

    @ApiModelProperty(value = "IP地址")
    @TableField("SLLO_IP")
    private String slloIp;

    @ApiModelProperty(value = "内容")
    @TableField("SLLO_DESCRIPT")
    private String slloDescript;

    @ApiModelProperty(value = "位置")
    @TableField("SLLO_LOCATION")
    private String slloLocation;

    @ApiModelProperty(value = "状态（0--成功1--失败）")
    @TableField("SLLO_STATUS")
    @Dict(dictCode = "sllo_status")
    private Integer slloStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("SLLO_CREATETIME")
    private Date slloCreateTime;

    @ApiModelProperty(value = "删除状态（0--未删除1--已删除）")
    @TableField("SLLO_DEL_FLAG")
    @TableLogic
    private Integer slloDelFlag;


    @Override
    protected Serializable pkVal() {
        return this.slloId;
    }

}
