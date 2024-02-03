package com.luos.console.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 数据库字段信息对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("information_schema.COLUMNS")
@ApiModel(value = "SysDataTableColumn对象", description = "数据库字段信息对象")
public class SysDataTableColumn extends Model<SysDataTableColumn> {

    @ApiModelProperty(value = "数据库名")
    @TableField("table_schema")
    private String tableSchema;

    @ApiModelProperty(value = "表名")
    @TableField("table_name")
    private String tableName;

    @ApiModelProperty(value = "字段名")
    @TableField("column_name")
    private String columnName;

    @ApiModelProperty(value = "字段说明")
    @TableField("column_comment")
    private String columnComment;

    @ApiModelProperty(value = "默认值")
    @TableField("column_default")
    private String columnDefault;

    @ApiModelProperty(value = "索引类型（可包含的值有PRI，代表主键，UNI，代表唯一键，MUL，可重复）")
    @TableField("column_key")
    private String columnKey;

    @ApiModelProperty(value = "其他信息（主键的自增类型）")
    @TableField("extra")
    private String extra;

    @ApiModelProperty(value = "字符集")
    @TableField("character_set_name")
    private String characterSetName;

    @ApiModelProperty(value = "排序规则")
    @TableField("collation_name")
    private String collationName;

    @ApiModelProperty(value = "字段类型")
    @TableField("column_type")
    private String columnType;

    @ApiModelProperty(value = "是否允许为空")
    @TableField("is_nullable")
    private String isNullable;

    @ApiModelProperty(value = "字段编号")
    @TableField("ordinal_position")
    private String ordinalPosition;
}
