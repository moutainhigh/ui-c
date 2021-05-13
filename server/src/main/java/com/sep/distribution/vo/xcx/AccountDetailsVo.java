package com.sep.distribution.vo.xcx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "账户详情对象", description = "账户详情对象")
public class AccountDetailsVo {

    @ApiModelProperty(value = "总收益")
    private BigDecimal addUpCashBack;

    @ApiModelProperty(value = "已提现")
    private BigDecimal totalWithdraw;

    @ApiModelProperty(value = "待提现")
    private BigDecimal balance;

}