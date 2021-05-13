package com.sep.sku.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *  用户订单统计信息
 *
 * @author zhangkai
 * @date 2020年03月14日 22:08
 */
@Data
@ApiModel(value="订单统计信息", description="订单统计信息")
public class StatisticalOrderInfo {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "总数量")
    private int totalNum;
}