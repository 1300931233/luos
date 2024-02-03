package com.asurplus.console.system.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 后台管理员信息表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_MANAGER")
@ApiModel(value = "SysUserInfo对象", description = "用户信息表")
public class SysManager extends Model<SysManager> {


    @ApiModelProperty(value = "ID")
    @TableId(value = "SMAN_ID", type = IdType.AUTO)
    private Integer smanId;

    @Excel(name = "登录账号", width = 15)
    @ApiModelProperty(value = "登录账号")
    @TableField("SMAN_ACCOUNT")
    private String smanAccount;

    @ApiModelProperty(value = "登录密码")
    @TableField("SMAN_PASSWORD")
    private String smanPassword;

    @Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    @TableField("SMAN_NAME")
    private String smanName;

    @Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    @TableField("SMAN_PHONE")
    private String smanPhone;

    @ApiModelProperty(value = "头像")
    @TableField("SMAN_AVATAR")
    private String smanAvatar;

    @Excel(name = "性别", width = 15, dict = "user_sex")
    @ApiModelProperty(value = "性别（0--未知1--男2--女）")
    @TableField("SMAN_SEX")
    @Dict(dictCode = "user_sex")
    private Integer smanSex;

    @ApiModelProperty(value = "邮箱地址")
    @TableField("SMAN_EMAIL")
    private String smanEmail;

    @Excel(name = "状态", width = 15, dict = "user_status")
    @ApiModelProperty(value = "状态（0--正常1--冻结）")
    @TableField("SMAN_STATUS")
    @Dict(dictCode = "user_status")
    private Integer smanStatus;

    @ApiModelProperty(value = "创建人")
    @TableField("SMAN_CREATEUSER_ID")
    private Integer smanCreateUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField("SMAN_CREATE_TIME")
    private Date smanCreateTime;

    @ApiModelProperty(value = "修改人")
    @TableField("SMAN_UPDATEUSER_ID")
    private Integer smanUpdateUserId;

    @ApiModelProperty(value = "修改时间")
    @TableField("SMAN_UPDATE_TIME")
    private Date smanUpdateTime;

    @ApiModelProperty(value = "删除状态（0--未删除1--已删除）")
    @TableField("SMAN_DEL_FLAG")
//  @TableLogic
    private Integer smanDelFlag;

    /**
     * 用户token
     */
    @TableField(exist = false)
    private String token;


    @Override
    protected Serializable pkVal() {
        return this.smanId;
    }

}
