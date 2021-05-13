package com.sep.distribution.vo.xcx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "资金变动详情对象", description = "资金变动详情对象")
public class FundChangeDetailsVo {

    @ApiModelProperty(value = "资金变动类型")
    private Integer fundChangeType;

    @ApiModelProperty(value = "发生时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "余额")
    private BigDecimal afterAmount;

    @ApiModelProperty(value = "变动金额")
    private BigDecimal amount;

}