package com.sep.sku.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="Sku保存属性信息", description="Sku保存属性信息")
public class SkuPropertySaveInfo {

    @ApiModelProperty(value = "商品属性字典表id，自定义属性为空")
    private Integer propertyDictId;

    @ApiModelProperty(value = "商品属性名称，如颜色、尺码等")
    private String propertyName;

    @ApiModelProperty(value = "商品属性可选值字典ID集合，自定义属性为空")
    private List<Integer> propertyValueDictId;

    @ApiModelProperty(value = "商品属性可选值自定义集合，如红色、绿色或S码、M码")
    private List<String> propertyValue;
}