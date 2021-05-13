package com.sep.distribution.vo.xcx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "商品属性选择信息", description = "商品属性选择信息")
@Data
public class OrderSkuPropertyRespVo {

    @ApiModelProperty("商品属性可选值字典表ID，自定义属性为空")
    private Integer propertyValueDictId;
    @ApiModelProperty("商品属性可选值名称，如红色、绿色或S码、M码")
    private String propertyValueName;

}