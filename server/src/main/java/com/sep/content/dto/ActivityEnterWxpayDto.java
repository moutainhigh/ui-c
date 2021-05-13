package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActivityEnterWxpayDto {
    @ApiModelProperty(value = "活动id")
    private Integer activityId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "订单号")
    private String activityNo;
    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "微信第三方交易单号")
    private String wxTradeNo;
}
