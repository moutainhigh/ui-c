package com.sep.introduction.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IntroductionDto {
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "图片")
    private String picture;
}
