package com.sep.distribution.vo.background;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "分销身份详情对象", description = "分销身份详情对象")
public class CashBackDetailsVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "返现金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "受益人ID")
    private Integer beneficiary;

    @ApiModelProperty(value = "收益人手机号")
    private String beneficiaryPhone;

    @ApiModelProperty(value = "消费者ID")
    private Integer consumer;

    @ApiModelProperty(value = "消费者手机号")
    private String consumerPhone;

    @ApiModelProperty(value = "返现等级")
    private Integer rank;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal monetary;

    @ApiModelProperty(value = "消费时间")
    private LocalDateTime consumeTime;

    @ApiModelProperty(value = "返现方式")
    private Integer cashBackWay;

    @ApiModelProperty(value = "返现利息")
    private Integer interest;

}