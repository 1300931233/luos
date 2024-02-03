package com.asurplus.api.tracq.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CallingCardAskVo {

    @ApiModelProperty(value = "行业")
    private String uccaIndustry;

    @ApiModelProperty(value = "省")
    private String uccaProvince;

    @ApiModelProperty(value = "市")
    private String uccaCity;

    @ApiModelProperty(value = "区")
    private String uccaArea;

    @ApiModelProperty(value = "查询第几页",required = true)
    private Integer page;

    @ApiModelProperty(value = "每页条数",required = true)
    private Integer limit;

    @ApiModelProperty(value = "请求Key")
    private String key;

    public CallingCardAskVo(String uccaIndustry, String uccaProvince, String uccaCity, String uccaArea, Integer page, Integer limit, String key) {
        this.uccaIndustry = uccaIndustry;
        this.uccaProvince = uccaProvince;
        this.uccaCity = uccaCity;
        this.uccaArea = uccaArea;
        if(null == page || page < 1){
            page = 1;
        }
        this.page = page;
        if(null == limit || limit < 1){
            limit = 10;
        }
        this.limit = limit;
        this.key = key;
    }
}
