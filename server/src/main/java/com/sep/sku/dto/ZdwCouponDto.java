package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZdwCouponDto {


    private Integer orderSkuId;

    @ApiModelProperty(value = "1折扣卷，2代金卷")
    private Integer skuType;

    @ApiModelProperty(value = "折到位商品价格")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "折扣卷（几折）")
    private Integer rebate;

    @ApiModelProperty(value = "代金卷金额")
    private BigDecimal replaceCash;

    @ApiModelProperty(value = "商品Id")
    private  Integer facilitatoSkuId;




}
