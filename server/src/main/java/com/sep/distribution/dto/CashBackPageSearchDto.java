package com.sep.distribution.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "返现分页查询请求对象", description = "返现分页查询请求对象")
public class CashBackPageSearchDto {

    @ApiModelProperty(value = "收益人ID")
    private Integer beneficiary;

    @ApiModelProperty(value = "消费者ID")
    private Integer consumer;

    @ApiModelProperty(value = "收益人手机号")
    private String beneficiaryPhone;

    @ApiModelProperty(value = "消费者手机号")
    private String consumerPhone;

    @ApiModelProperty(value = "返现等级")
    private Integer rank;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "当前页")
    private Long currentPage = 0L;

    @ApiModelProperty(value = "每页长度")
    private Long pageSize = 10L;

}