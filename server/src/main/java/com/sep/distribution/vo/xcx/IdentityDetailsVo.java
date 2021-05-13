package com.sep.distribution.vo.xcx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分销身份详情对象", description = "分销身份详情对象")
public class IdentityDetailsVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "身份名称")
    private String name;

    @ApiModelProperty(value = "返现方式")
    private Integer cashBackWay;

    @ApiModelProperty(value = "一级返现利息")
    private Integer stairInterest;

    @ApiModelProperty(value = "二级返现利息")
    private Integer secondLevelInterest;

}