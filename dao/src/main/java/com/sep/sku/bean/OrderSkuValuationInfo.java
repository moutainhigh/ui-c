package com.sep.sku.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单商品计价参数
 *
 * @author zhangkai
 * @date 2019年12月28日 23:34
 */
@Data
@ApiModel(value="订单商品计价参数", description="订单商品计价参数")
public class OrderSkuValuationInfo {

    @ApiModelProperty(value = "一个用户的购物车内唯一标识，此标识通过skuId+skuProperty生成")
    private String skuUniqueKey;

    @ApiModelProperty(value = "商品ID")
    private Integer skuId;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "购买数量")
    private int buyNum = 1;

//    @ApiModelProperty(value = "折到位优惠卷")
//    private String encryptParam;

}
