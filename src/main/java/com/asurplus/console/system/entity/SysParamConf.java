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
 * 系统参数设置
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_PARAM_CONF")
@ApiModel(value = "SysParamConf对象", description = "系统参数设置")
public class SysParamConf extends Model<SysParamConf> {

    @TableId(value = "SPCO_ID", type = IdType.AUTO)
    private Integer spcoId;

    @ApiModelProperty(value = "登录验证码长度")
    @TableField("SPCO_LOGIN_CODE_LEN")
    private Integer spcoLoginCodeLen;

    @ApiModelProperty(value = "登录验证码类型（0--纯数字1--纯字母2--数字+字母）")
    @TableField("SPCO_LOGIN_CODE_TYPE")
    @Dict(dictCode = "login_code_type")
    private Integer spcoLoginCodeType;

    @ApiModelProperty(value = "短信验证码长度")
    @TableField("SPCO_SMS_CODE_LEN")
    private Integer spcoSmsCodeLen;

    @ApiModelProperty(value = "短信验证码类型（0--纯数字1--纯字母2--数字+字母）")
    @TableField("SPCO_SMS_CODE_TYPE")
    @Dict(dictCode = "sms_code_type")
    private Integer spcoSmsCodeType;

    @ApiModelProperty(value = "默认登录密码")
    @TableField("SPCO_DEFAULT_PASSWORD")
    private String spcoDefaultPassword;

    @ApiModelProperty(value = "创建时间")
    @TableField("SPCO_CREATEUSER_TIME")
    private Date spcoCreateTime;

    @Override
    protected Serializable pkVal() {
        return this.spcoId;
    }

}
