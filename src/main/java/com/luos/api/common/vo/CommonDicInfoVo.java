package com.luos.api.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 字典数据公共vo
 * @ClassName CommonDicInfoVo
 **/
@Data
@Accessors(chain = true)
@ApiModel(value="字典数据公共vo")
public class CommonDicInfoVo {

    @ApiModelProperty("编码")
    private String sddeCode;

    @ApiModelProperty("名称")
    private String sddeName;


}
