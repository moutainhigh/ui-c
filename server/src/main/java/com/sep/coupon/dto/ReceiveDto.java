package com.sep.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "领取请求对象", description = "领取请求对象")
public class ReceiveDto extends BaseDto {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "优惠券ID")
    @NotNull(message = "优惠卷ID不能为空")
    private Integer couponId;

}