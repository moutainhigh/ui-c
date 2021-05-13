package com.sep.distribution.vo.xcx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "提现详情对象", description = "提现详情对象")
public class WithdrawDetailsVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "提现帐号类型")
    private Integer accountType;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "提现帐号")
    private String account;

    @ApiModelProperty(value = "开户城市")
    private String openAccountCity;

    @ApiModelProperty(value = "开户行")
    private String bankOfDeposit;

    @ApiModelProperty(value = "提现状态")
    private Integer state;

    @ApiModelProperty(value = "提现时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "提现金额")
    private BigDecimal amount;

}