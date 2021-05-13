package com.sep.sku.vo;

import com.sep.sku.bean.SettlementPropertyInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * 购物车商品信息
 *
 * @author zhangkai
 * @date 2019年12月28日 23:25
 */
@Data
public class CartSkuVo {

    @ApiModelProperty(value = "一个用户的购物车内唯一标识，此标识通过skuId+skuProperty生成")
    private String skuUniqueKey;

    @ApiModelProperty(value = "商品ID")
    private Integer skuId;

//    @ApiModelProperty(value = "商品选择属性集合")
//    private List<SettlementPropertyInfo> settlementPropertyInfoList;

    @ApiModelProperty(value = "购买数量")
    private int buyNum = 1;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "商品首张主图url")
    private String skuFirstPictureUrl;

    @ApiModelProperty(value = "现价")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "积分兑换数量")
    private int integralNum;

    @ApiModelProperty(value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "代金或者折扣描述")
    private String rebateDepict;
}
