package com.sep.distribution.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InvoiceUserDto {
    private  Integer id;
    @ApiModelProperty(value = "活动id")
    private String orderId;
    @ApiModelProperty(value = "用户ID")
    private String token;
    @ApiModelProperty(value = "备注")
    private String remark1;
    @ApiModelProperty(value = "备注")
    private String remark2;
    @ApiModelProperty(value = "备注")
    private String remark3;
    @ApiModelProperty(value = "备注")
    private String remark4;
    @ApiModelProperty(value = "备注")
    private String remark5;
    @ApiModelProperty(value = "备注")
    private String remark6;
    @ApiModelProperty(value = "备注")
    private String remark7;
    @ApiModelProperty(value = "备注")
    private String remark8;
    @ApiModelProperty(value = "备注")
    private String remark9;
    @ApiModelProperty(value = "备注")
    private String remark10;
}
