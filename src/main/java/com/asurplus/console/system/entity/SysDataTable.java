package com.asurplus.console.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 数据库信息对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("information_schema.TABLES")
@ApiModel(value = "SysDataTable对象", description = "数据库信息对象")
public class SysDataTable extends Model<SysDataTable> {

    @ApiModelProperty(value = "数据库名")
    @TableField("table_schema")
    private String tableSchema;

    @ApiModelProperty(value = "表名")
    @TableField("table_name")
    private String tableName;

    @ApiModelProperty(value = "表说明")
    @TableField("table_comment")
    private String tableComment;

    @ApiModelProperty(value = "存储引擎")
    @TableField("engine")
    private String engine;

    @ApiModelProperty(value = "编码格式")
    @TableField("table_collation")
    private String tableCollation;

    @ApiModelProperty(value = "数据量")
    @TableField("table_rows")
    private String tableRows;

    @ApiModelProperty(value = "索引大小")
    @TableField("index_length")
    private String indexLength;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private String updateTime;
}
