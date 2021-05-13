package com.sep.sku.vo;


import com.sep.sku.vo.ServerSearchOrderSkuPropertyRespVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServerSearchOrderSkuRespVo{

    @ApiModelProperty(value = "商品首张图片")
    private String skuFirstPictureUrl;

    @ApiModelProperty(value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "消费总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "消费总积分")
    private int totalIntegral;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "支付方式，0：微信，1：积分")
    private int payWay;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "微信id")
    private String unionId;

    @ApiModelProperty(value = "状态，0：待支付，1：已支付，2：已取消，3：已退款，4：已完成")
    private int status;

//    @ApiModelProperty(value = "邮寄状态，0：待邮寄，1：已邮寄，2：已收货")
//    private int expressStatus;

//    @ApiModelProperty(value = "自提状态，0：待自提，1：已自提")
//    private int fetchStatus;

//    @ApiModelProperty(value = "发货时间")
//    private LocalDateTime expressTime;
//
//    @ApiModelProperty(value = "自提时间")
//    private LocalDateTime fetchTime;

//    @ApiModelProperty(value = "快递公司id")
//    private int expressType;
//
//    @ApiModelProperty(value = "快递单号")
//    private String expressNo;

//    @ApiModelProperty(value = "店铺id")
//    private Integer shopId;

    @ApiModelProperty(value = "商品id")
    private Integer skuId;

    @ApiModelProperty(value = "购买数量")
    private Integer buyNum;

    @ApiModelProperty(value = "sku成交价")
    private BigDecimal skuPrice;

    @ApiModelProperty(value = "sku原价")
    private BigDecimal originPrice;

    @ApiModelProperty(value = "sku兑换所需积分")
    private int skuIntegral;

    @ApiModelProperty(value = "代金或者折扣描述")
    private String rebateDepict;

    @ApiModelProperty(value = "商品类型")
    private Integer skuType;


    @ApiModelProperty(value = "商品属性选择信息")
    private List<ServerSearchOrderSkuPropertyRespVo> propertyValueInfoList;

}