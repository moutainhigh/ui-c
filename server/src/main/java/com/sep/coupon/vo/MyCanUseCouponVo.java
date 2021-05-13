package com.sep.coupon.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel(value = "我能使用的优惠卷详情对象", description = "我能使用的优惠卷详情对象")
public class MyCanUseCouponVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal monetary;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal cashRebate;

    @ApiModelProperty(value = "使用开始时间")
    private LocalDate useStartDate;

    @ApiModelProperty(value = "使用结束时间")
    private LocalDate useEndDate;

    @ApiModelProperty(value = "使用范围")
    private Integer useScope;

    @ApiModelProperty(value = "优惠共享类型")
    private Integer discountsShareType;

}