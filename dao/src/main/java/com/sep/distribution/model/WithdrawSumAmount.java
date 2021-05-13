package com.sep.distribution.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 提现表
 * </p>
 *
 * @author litao
 * @since 2020-01-07
 */
@Data
@ApiModel(value="Withdraw对象", description="提现表")
public class WithdrawSumAmount {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

}