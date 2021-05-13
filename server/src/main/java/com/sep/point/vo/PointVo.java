package com.sep.point.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="用户积分详情对象", description="用户积分详情对象")
public class PointVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "积分余额")
    private Integer balance;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

}