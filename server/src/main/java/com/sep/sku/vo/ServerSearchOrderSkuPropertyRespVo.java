package com.sep.sku.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServerSearchOrderSkuPropertyRespVo {

    @ApiModelProperty(value = "商品属性可选值字典表ID，自定义属性为空")
    private Integer propertyValueDictId;

    @ApiModelProperty(value = "商品属性可选值名称，如红色、绿色或S码、M码")
    private String propertyValueName;
}