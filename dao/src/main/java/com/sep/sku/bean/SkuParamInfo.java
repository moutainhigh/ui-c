package com.sep.sku.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="Sku参数信息", description="Sku参数信息")
public class SkuParamInfo{

    @ApiModelProperty(value = "参数名，如品牌、产地")
    private String paramName;

    @ApiModelProperty(value = "参数值，如小米、上海")
    private String paramValue;
}