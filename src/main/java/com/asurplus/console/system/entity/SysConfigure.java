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
import java.util.Date;

/**
 * 系统设置
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_CONFIGURE")
@ApiModel(value = "SysConfigure对象", description = "系统设置")
public class SysConfigure extends Model<SysConfigure> {

    @ApiModelProperty(value = "系统设置ID")
    @TableId(value = "SCON_ID", type = IdType.AUTO)
    private Integer sconId;

    @ApiModelProperty(value = "网站标题")
    @TableField("SCON_WEBSITE_TITLE")
    private String sconWebsiteTitle;

    @ApiModelProperty(value = "网站图版权信息")
    @TableField("SCON_COPYRIGHT")
    private String sconCopyright;

    @ApiModelProperty(value = "创建时间")
    @TableField("SCON_CREATEUSER_TIME")
    private Date sconCreateTime;


    @Override
    protected Serializable pkVal() {
        return this.sconId;
    }

}
