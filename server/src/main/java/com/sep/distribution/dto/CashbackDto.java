package com.sep.distribution.dto;

import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CashbackDto {

    @NotNull(message = "消费金额不能为空")
    @Min(value = 0, message = "消费金额不能为负数")
    private BigDecimal monetary;

    @NotNull(message = "消费者不能为空")
    private Integer consumer;

    @NotNull(message = "订单不能为空")
    private Integer orderId;

    @NotNull(message = "订单编号不能为空")
    @NotEmpty(message = "订单编号不能为空")
    @Length(max = 64, message = "订单编号长度不能超过64个字符")
    private String orderNo;

    @NotNull(message = "消费时间不能为空")
    private LocalDateTime consumeTime;

}