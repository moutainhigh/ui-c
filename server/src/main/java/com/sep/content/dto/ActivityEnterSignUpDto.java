package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivityEnterSignUpDto {
    @ApiModelProperty(value = "用户填写项ID")
    private Integer id;
    @ApiModelProperty(value = "用户报名信息")
    private String information;
}
