package com.sep.sku.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 确认订单商品信息
 *
 * @author zhangkai
 * @date 2019年12月28日 23:34
 */
@Data
@ApiModel(value="确认订单商品返回信息", description="确认订单商品信息")
public class SettlementSkuInfo{

    @ApiModelProperty(value = "商品ID")
    private Integer skuId;

    @ApiModelProperty(value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "商品简介")
    private String introduction;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "原价")
    private BigDecimal originPrice;

    @ApiModelProperty(value = "商品库存，0：不设库存")
    private Integer stockNum;

    @ApiModelProperty(value = "商品首张主图url")
    private String skuFirstPictureUrl;

    @ApiModelProperty(value = "购买数量")
    private int buyNum = 1;





}
