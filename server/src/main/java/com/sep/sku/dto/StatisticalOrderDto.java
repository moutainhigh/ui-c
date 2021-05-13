package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;

import java.util.List;

/**
 * 统计订单 参数
 */
@Data
public class StatisticalOrderDto {

    @ApiModelProperty("用户ID集合")
    @NotEmpty
    private List<Integer> userIdList;

    @ApiModelProperty("支付方式,0:微信，1：积分")
    private Integer payWay;
}