package com.luos.console.system.entity;

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
 * 操控日志实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_HANDLE_LOG")
@ApiModel(value = "SysHandleLog对象", description = "")
public class SysHandleLog extends Model<SysHandleLog> {


    @TableId(value = "SHLO_ID", type = IdType.AUTO)
    private Integer shloId;

    @ApiModelProperty(value = "操作模块")
    @TableField("SHLO_MODEL")
    private String shloModel;

    @ApiModelProperty(value = "请求地址")
    @TableField("SHLO_URL")
    private String shloUrl;

    @ApiModelProperty(value = "携带参数")
    @TableField("SHLO_PARAM")
    private String shloParam;

    @ApiModelProperty(value = "用户ip")
    @TableField("SHLO_IP")
    private String shloIp;

    @ApiModelProperty(value = "位置")
    @TableField("SHLO_LOCATION")
    private String shloLocation;

    @ApiModelProperty(value = "耗时")
    @TableField("SHLO_SPEND_TIME")
    private Long shloSpendTime;

    @ApiModelProperty(value = "创建人")
    @TableField("SHLO_CREATEUSER_ID")
    private Integer shloCreateUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField("SHLO_CREATE_TIME")
    private Date shloCreateTime;

    @ApiModelProperty(value = "删除状态（0--未删除1--已删除）")
    @TableField("SHLO_DELFLAG")
    @TableLogic
    private Integer shloDelFlag;


    @Override
    protected Serializable pkVal() {
        return this.shloId;
    }

}
