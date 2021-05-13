package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchFacilitatorDto {

    @ApiModelProperty(value = "服务商名称")
    private String facilitatorName;

    @ApiModelProperty(value = "服务商负责人")
    private String leadingOfficial;

    @ApiModelProperty(value = "服务商电话")
    private String phoneNum;

}
