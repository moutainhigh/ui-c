package com.sep.sku.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 优惠券计价商品信息
 *
 * @author zhangkai
 * @date 2019年12月28日 23:34
 */
@Data
@ApiModel(value="优惠券计价商品信息", description="优惠券计价商品信息")
public class CouponValuationSkuInfo {

    @ApiModelProperty(value = "一个用户的购物车内唯一标识，此标识通过skuId+skuProperty生成")
    private String skuUniqueKey;

    @ApiModelProperty(value = "商品ID")
    private Integer skuId;

    @ApiModelProperty(value = "购买数量")
    private int buyNum = 1;

}
