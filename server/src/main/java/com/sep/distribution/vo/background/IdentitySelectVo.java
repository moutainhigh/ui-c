package com.sep.distribution.vo.background;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分销身份下拉对象", description = "分销身份下拉对象")
public class IdentitySelectVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "身份名称")
    private String name;

}