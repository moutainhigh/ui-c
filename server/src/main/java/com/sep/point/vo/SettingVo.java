package com.sep.point.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="设置详情对象", description="设置详情对象")
public class SettingVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "设置类型")
    private Integer type;

    @ApiModelProperty(value = "设置标识")
    private Integer code;

    @ApiModelProperty(value = "设置值")
    private Integer value;

}