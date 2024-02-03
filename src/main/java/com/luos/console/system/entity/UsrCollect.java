package com.luos.console.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("usr_collect")
@ApiModel(value="UsrCollect对象", description="")
public class UsrCollect extends Model<UsrCollect> {


    @TableId(value = "UCOL_ID", type = IdType.AUTO)
    private Integer ucolId;

    @ApiModelProperty(value = "名片id")
    @TableField("UCOL_UCCA_ID")
    private Integer ucolUccaId;

    @ApiModelProperty(value = "用户id")
    @TableField("UCOL_UUIN_ID")
    private Integer ucolUuinId;

    @ApiModelProperty(value = "收藏时间")
    @TableField("UCOL_TIME")
    private Integer ucolTime;


    @Override
    protected Serializable pkVal() {
        return this.ucolId;
    }

}
