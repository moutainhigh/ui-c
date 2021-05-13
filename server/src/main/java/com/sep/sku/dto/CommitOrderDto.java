package com.sep.sku.dto;

import com.sep.sku.bean.CommitOrderSkuInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

import java.util.List;

/**
 * 提交订单 参数
 */
@Data
public class CommitOrderDto{
    @ApiModelProperty(value = "一个用户的购物车内唯一标识，此标识通过skuId+skuProperty生成")
    private String skuUniqueKey;
//    @ApiModelProperty("购买商品信息")
//    private List<CommitOrderSkuInfo> buySkuList;
    @ApiModelProperty(value = "购买数量")
    private int buyNum = 1;
    @NotNull(message = "商品id不能为空")
    @ApiModelProperty(value = "商品ID")
    private Integer skuId;
    @ApiModelProperty("手机号码")
    private List<String> phones;
    @ApiModelProperty("支付方式")
    private int payWay;

    @ApiModelProperty("收货地址ID")
    private Integer takeInfoId;

    @ApiModelProperty("ip")
    private String ip;
    @ApiModelProperty("token")
    private String token;

//    @ApiModelProperty("选择的优惠券id")
//    private Integer couponId;
    @ApiModelProperty("备注")
    @NotNull(message = "备注不能为空")
    private String remarks;
    @ApiModelProperty("下单来源,0：商品详情页，1：购物车")
    private int source;


}