package com.sep.sku.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="确认订单商品属性信息", description="确认订单商品属性信息")
public class SettlementPropertyInfo {

    @ApiModelProperty(value = "商品属性表ID")
    private Integer skuPropertyId;

    @ApiModelProperty(value = "商品属性名称")
    private String skuPropertyName;

    @ApiModelProperty(value = "商品属性的值字典ID，自定义属性为空")
    private Integer propertyValueDictId;

    @ApiModelProperty(value = "商品属性值，如：S码、M码")
    private String propertyValue;
}