package com.sep.distribution.vo.xcx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "枚举项对象", description = "枚举项对象")
public class EnumItemVo {

    @ApiModelProperty(value = "编码")
    private Integer id;

    @ApiModelProperty(value = "身份名称")
    private String text;

}