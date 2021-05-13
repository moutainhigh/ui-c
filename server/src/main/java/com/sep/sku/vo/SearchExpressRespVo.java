package com.sep.sku.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchExpressRespVo{

    @ApiModelProperty(value = "快递公司ID")
    private Integer id;

    @ApiModelProperty(value = "快递公司名称")
    private String expressName;
}