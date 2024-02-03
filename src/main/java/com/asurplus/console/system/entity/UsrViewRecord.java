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
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xlp
 * @since 2023-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usr_view_record")
@ApiModel(value="UsrViewRecord对象", description="")
public class UsrViewRecord extends Model<UsrViewRecord> {


    @TableId(value = "UVRE_ID", type = IdType.AUTO)
    private Integer uvreId;

    @ApiModelProperty(value = "用户id")
    @TableField("UVRE_UUIN_ID")
    private Integer uvreUuinId;

    @ApiModelProperty(value = "名片id")
    @TableField("UVRE_UCCA_ID")
    private Integer uvreUccaId;

    @TableField("UVRE_CREATE_TIME")
    private LocalDateTime uvreCreateTime;


    @Override
    protected Serializable pkVal() {
        return this.uvreId;
    }

}
