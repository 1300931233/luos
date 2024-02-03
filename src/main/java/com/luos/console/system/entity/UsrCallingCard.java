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
 * <p>
 * 
 * </p>
 *
 * @author xlp
 * @since 2023-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usr_calling_card")
@ApiModel(value="UsrCallingCard对象", description="")
public class UsrCallingCard extends Model<UsrCallingCard> {


    @TableId(value = "UCCA_ID", type = IdType.AUTO)
    private Integer uccaId;

    @ApiModelProperty(value = "用户id")
    @TableField("UCCA_UUIN_ID")
    private Integer uccaUuinId;

    @ApiModelProperty(value = "图片")
    @TableField("UCCA_IMAGE")
    private String uccaImage;

    @ApiModelProperty(value = "名字")
    @TableField("UCCA_NAME")
    private String uccaName;

    @ApiModelProperty(value = "首字母拼音")
    @TableField("UCCA_SPELL")
    private String uccaSpell;

    @ApiModelProperty(value = "电话")
    @TableField("UCCA_PHONE")
    private String uccaPhone;

    @ApiModelProperty(value = "公司")
    @TableField("UCCA_COMPANY")
    private String uccaCompany;

    @ApiModelProperty(value = "职务")
    @TableField("UCCA_JOB")
    private String uccaJob;

    @ApiModelProperty(value = "行业")
    @TableField("UCCA_INDUSTRY")
    private String uccaIndustry;

    @ApiModelProperty(value = "省")
    @TableField("UCCA_PROVINCE")
    private String uccaProvince;

    @ApiModelProperty(value = "市")
    @TableField("UCCA_CITY")
    private String uccaCity;

    @ApiModelProperty(value = "区")
    @TableField("UCCA_AREA")
    private String uccaArea;

    @ApiModelProperty(value = "详细地址")
    @TableField("UCCA_ADDRESS")
    private String uccaAddress;

    @ApiModelProperty(value = "简介")
    @TableField("UCCA_SYNOPSIS")
    private String uccaSynopsis;

    @ApiModelProperty(value = "详细地址")
    @TableField("UCCA_ADDRESS")
    private String uccaAddress2;

    @ApiModelProperty(value = "状态 1：禁用  0：启用")
    @TableField("UCCA_STATUS")
    private Integer uccaStatus;

    @ApiModelProperty(value = "是否公开 1：不公开  0：公开")
    @TableField("UCCA_DISCLOSE")
    private Integer uccaDisclose;

    @ApiModelProperty(value = "是否允许转发 1：不允许  0：允许")
    @TableField("UCCA_TRANSMIT")
    private Integer uccaTransmit;

    @ApiModelProperty(value = "所属模板")
    @TableField("UCCA_TEMPLATE")
    private String uccaTemplate;

    @ApiModelProperty(value = "创建时间")
    @TableField("UCCA_CREAT_TIME")
    private Date uccaCreatTime;


    @Override
    protected Serializable pkVal() {
        return this.uccaId;
    }

}
