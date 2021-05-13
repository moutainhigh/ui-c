package com.sep.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "获取优惠卷详情请求对象", description = "获取优惠卷详情请求对象")
@Data
public class GetCouponInput {

    @ApiModelProperty(value = "优惠卷ID")
    private Integer couponId;

}