package com.sep.distribution.vo.background;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="设置详情对象", description="设置详情对象")
public class SettingVo {

    @ApiModelProperty(value = "设置类型")
    private Integer type;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "设置值")
    private String value;

}