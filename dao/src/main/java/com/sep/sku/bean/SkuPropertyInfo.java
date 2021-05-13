package com.sep.sku.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="Sku属性信息", description="Sku属性信息")
public class SkuPropertyInfo{

    @ApiModelProperty(value = "商品属性表ID")
    private Integer skuPropertyId;

    @ApiModelProperty(value = "商品属性字典表id，自定义属性为空")
    private Integer propertyDictId;

    @ApiModelProperty(value = "商品属性名称，如颜色、尺码等")
    private String propertyName;

    @ApiModelProperty(value = "商品属性可选值集合")
    private List<SkuPropertyValueInfo> propertyValueList;
}