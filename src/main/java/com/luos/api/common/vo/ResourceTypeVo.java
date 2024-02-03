package com.luos.api.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户反馈类型
 * @ClassName UserRegisterVo
 * @Date 2021-05-12 15:30
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@ApiModel(value="用户反馈类型")
public class ResourceTypeVo {

    @ApiModelProperty("类型名称")
    private String sddeName;

    @ApiModelProperty("类型编码")
    private String sddeCode;

}
