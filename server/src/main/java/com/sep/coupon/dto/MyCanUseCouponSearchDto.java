package com.sep.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;

@Data
@ApiModel(value = "我可以使用的优惠卷查询请求对象", description = "我可以使用的优惠卷查询请求对象")
public class MyCanUseCouponSearchDto extends BaseDto {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "消费金额")
    @NotNull(message = "消费金额不能为空")
    @Min(value = 0, message = "消费金额不能为负数")
    private BigDecimal monetary;

}