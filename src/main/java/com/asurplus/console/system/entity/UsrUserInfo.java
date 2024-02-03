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
@TableName("usr_user_info")
@ApiModel(value="UsrUserInfo对象", description="")
public class UsrUserInfo extends Model<UsrUserInfo> {


    @TableId(value = "UUIN_ID", type = IdType.AUTO)
    private Integer uuinId;

    @ApiModelProperty(value = "名称")
    @TableField("UUIN_NAME")
    private String uuinName;

    @ApiModelProperty(value = "电话")
    @TableField("UUIN_PHONE")
    private String uuinPhone;

    @ApiModelProperty(value = "openid")
    @TableField("UUIN_OPENID")
    private String uuinOpenid;

    @ApiModelProperty(value = "性别")
    @TableField("UUIN_SEX")
    private String uuinSex;

    @ApiModelProperty(value = "创建时间")
    @TableField("UUIN_CREATE_TIME")
    private Date uuinCreateTime;

    @ApiModelProperty(value = "性别")
    @TableField(exist = false)
    private String token;


    @Override
    protected Serializable pkVal() {
        return this.uuinId;
    }

}
