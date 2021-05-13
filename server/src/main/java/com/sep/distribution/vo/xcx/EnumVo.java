package com.sep.distribution.vo.xcx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "枚举对象", description = "枚举对象")
public class EnumVo {

    @ApiModelProperty(value = "编码")
    private String id;

    @ApiModelProperty(value = "身份名称")
    private String text;

    @ApiModelProperty(value = "枚举项")
    List<EnumItemVo> items;

}