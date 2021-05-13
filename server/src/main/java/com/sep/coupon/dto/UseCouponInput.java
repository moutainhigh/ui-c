package com.sep.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel(value = "优惠卷使用请求对象", description = "优惠卷使用请求对象")
@Data
public class UseCouponInput {

    @ApiModelProperty(value = "优惠卷ID")
    @NotNull(message = "优惠卷ID不能为空")
    private Integer couponId;

    @ApiModelProperty(value = "消费金额")
    @NotNull(message = "消费金额不能为空")
    @Min(value = 0, message = "消费金额不能为负数")
    private BigDecimal monetary;

    @ApiModelProperty(value = "实际消费金额")
    @NotNull(message = "实际消费金额不能为空")
    @Min(value = 0, message = "实际消费金额不能为负数")
    private BigDecimal actualMonetary;

    @ApiModelProperty(value = "消费者ID")
    @NotNull(message = "消费者ID不能为空")
    private Integer consumer;

    @ApiModelProperty(value = "订单ID")
    @NotNull(message = "订单ID不能为空")
    private Integer orderId;

    @ApiModelProperty(value = "订单号")
    @NotNull(message = "订单号不能为空")
    @NotEmpty(message = "订单号不能为空")
    @Length(max = 64, message = "订单号不能超过64个字符")
    private String orderNo;

    @ApiModelProperty(value = "消费时间")
    @NotNull(message = "消费时间不能为空")
    private LocalDateTime consumeTime;

}