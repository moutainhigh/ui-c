package com.sep.sku.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="Sku属性可选值信息", description="Sku属性信息")
public class SkuPropertyValueInfo {

    @ApiModelProperty(value = "商品属性可选值字典表ID，自定义属性为空")
    private Integer propertyValueDictId;

    @ApiModelProperty(value = "商品属性可选值名称，如红色、绿色或S码、M码")
    private String propertyValueName;
}