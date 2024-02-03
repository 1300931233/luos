package com.asurplus.api.tracq.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CallingCardVo {

    @ApiModelProperty(value = "名片id")
    private Integer uccaId;

    @ApiModelProperty(value = "用户id")
    private Integer uccaUuinId;

    @ApiModelProperty(value = "图片路径")
    private String uccaImage;

    @ApiModelProperty(value = "图片文件")
    private MultipartFile uccaImageFile;

    @ApiModelProperty(value = "名字")
    private String uccaName;

    @ApiModelProperty(value = "电话")
    private String uccaPhone;

    @ApiModelProperty(value = "公司")
    private String uccaCompany;

    @ApiModelProperty(value = "职务")
    private String uccaJob;

    @ApiModelProperty(value = "行业")
    private String uccaIndustry;

    @ApiModelProperty(value = "详细地址")
    private String uccaAddress;

    @ApiModelProperty(value = "是否公开 1：公开  0：不公开")
    private Integer uccaDisclose;
}
