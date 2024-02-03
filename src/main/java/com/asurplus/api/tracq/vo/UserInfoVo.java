package com.asurplus.api.tracq.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoVo {

    @ApiModelProperty(value = "名称",required = true)
    private String uuinName;

    @ApiModelProperty(value = "电话",required = true)
    private String uuinPhone;

    @ApiModelProperty(value = "openid",required = true)
    private String uuinOpenid;

    @ApiModelProperty(value = "性别")
    private String uuinSex;
}
