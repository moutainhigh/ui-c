package com.sep.sku.vo;

import com.sep.sku.bean.SkuPropertyValueInfo;
import com.sep.sku.model.OrderSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SearchOrderSkuRespVo extends OrderSku {

    @ApiModelProperty(value = "商品首张图片")
    private String skuFirstPictureUrl;

    @ApiModelProperty(value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "消费总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "消费总积分")
    private int totalIntegral;

    @ApiModelProperty(value = "商品属性选择信息")
    private List<SkuPropertyValueInfo> propertyValueInfoList;

    @ApiModelProperty(value = "代金或者折扣描述")
    private String rebateDepict;

    @ApiModelProperty(value = "商品类型")
    private Integer skuType;

    @ApiModelProperty(value = "商品列表图")
    private String skuListImg;


}