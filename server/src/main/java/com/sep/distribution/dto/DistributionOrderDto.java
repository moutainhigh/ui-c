package com.sep.distribution.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "分销订单请求对象", description = "分销订单请求对象")
public class DistributionOrderDto extends BaseDto {

    private Integer userId;

    @ApiModelProperty("下单开始时间")
    private LocalDate startDate;

    @ApiModelProperty("下单结束时间")
    private LocalDate endDate;

    @ApiModelProperty("订单状态，0：待支付，1：已支付，2：已取消，3：已退款，4：已完成")
    private Integer status;

    @ApiModelProperty("支付类型，0：微信，1：积分")
    private Integer payWay;

    @ApiModelProperty("当前页")
    private Integer currentPage = 0;

    @ApiModelProperty("每页长度")
    private Integer pageSize = 20;

}