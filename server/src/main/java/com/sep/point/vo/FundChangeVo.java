package com.sep.point.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "积分变动对象", description = "积分变动对象")
public class FundChangeVo {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "变动原因")
    private String fundChangeType;

    @ApiModelProperty(value = "变动前金额")
    private Integer beforeAmount;

    @ApiModelProperty(value = "变动后金额")
    private Integer afterAmount;

    @ApiModelProperty(value = "变动金额")
    private Integer amount;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}