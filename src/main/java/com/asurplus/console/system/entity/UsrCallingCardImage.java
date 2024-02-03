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

/**
 * <p>
 * 
 * </p>
 *
 * @author xlp
 * @since 2023-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usr_calling_card_image")
@ApiModel(value="UsrCallingCardImage对象", description="")
public class UsrCallingCardImage extends Model<UsrCallingCardImage> {


    @TableId(value = "UCCI_ID", type = IdType.AUTO)
    private Integer ucciId;

    @ApiModelProperty(value = "图片地址")
    @TableField("UCCI_URL")
    private String ucciUrl;

    @ApiModelProperty(value = "图片标题")
    @TableField("UCCI_TITLE")
    private String ucciTitle;

    @ApiModelProperty(value = "图片显示顺序")
    @TableField("UCCI_INDEX")
    private Integer ucciIndex;


    @Override
    protected Serializable pkVal() {
        return this.ucciId;
    }

}
