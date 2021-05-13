package com.sep.distribution.vo.xcx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "分销订单商品VO", description = "分销订单商品VO")
public class DistributionOrderSkuVo {

    @ApiModelProperty("商品首张图片")
    private String skuFirstPictureUrl;
    @ApiModelProperty("商品名称")
    private String skuName;
    @ApiModelProperty("消费总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty("消费总积分")
    private int totalIntegral;
    @ApiModelProperty("商品id")
    private Integer skuId;
    @ApiModelProperty("购买数量")
    private Integer buyNum;
    @ApiModelProperty("sku成交价")
    private BigDecimal skuPrice;
    @ApiModelProperty(value = "sku原价")
    private BigDecimal originPrice;
    @ApiModelProperty("sku兑换所需积分")
    private Integer skuIntegral;
    @ApiModelProperty("支付方式，0：微信，1：积分")
    private Integer payWay;
    @ApiModelProperty(value = "代金或者折扣描述")
    private String rebateDepict;
    @ApiModelProperty(value = "商品类型")
    private Integer skuType;
    @ApiModelProperty("商品属性选择信息")
    private List<OrderSkuPropertyRespVo> propertyValueInfoList;

}