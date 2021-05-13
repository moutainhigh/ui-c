package com.sep.coupon.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "优惠卷详情对象", description = "优惠卷详情对象")
public class CouponDetailsOutPut {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "优惠券类型")
    private Integer type;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal monetary;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal cashRebate;

    @ApiModelProperty(value = "总数")
    private Integer total;

    @ApiModelProperty(value = "库存")
    private Integer inventory;

    @ApiModelProperty(value = "已领取")
    private Integer receive;

    @ApiModelProperty(value = "已使用量")
    private Integer used;

    @ApiModelProperty(value = "领取开始时间")
    private LocalDate receiveStartDate;

    @ApiModelProperty(value = "领取结束时间")
    private LocalDate receiveEndDate;

    @ApiModelProperty(value = "使用开始时间")
    private LocalDate useStartDate;

    @ApiModelProperty(value = "使用结束时间")
    private LocalDate useEndDate;

    @ApiModelProperty(value = "使用范围")
    private Integer useScope;

    @ApiModelProperty(value = "优惠共享类型")
    private Integer discountsShareType;

    @ApiModelProperty(value = "发行状态")
    private Integer publishStatus;

    @ApiModelProperty(value = "是否过期:0 未过期,1 已过期")
    private Integer expiresStatus;

    @ApiModelProperty(value = "是否领完:0 未领完,1 已领完")
    private Integer receiveStatus;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}