package com.sep.sku.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 提交订单商品信息
 *
 * @author zhangkai
 * @date 2019年12月28日 23:34
 */
@Data
@ApiModel(value="提交订单商品信息", description="提交订单商品信息")
public class CommitOrderSkuInfo {

    @ApiModelProperty(value = "一个用户的购物车内唯一标识，此标识通过skuId+skuProperty生成")
    private String skuUniqueKey;

    @ApiModelProperty(value = "商品ID")
    private Integer skuId;

//    @ApiModelProperty(value = "商品选择属性集合")
//    private List<SettlementPropertyInfo> settlementPropertyInfoList;

    @ApiModelProperty(value = "购买数量")
    private int buyNum = 1;
    @ApiModelProperty("手机号码")
    private List<String> phones;

//    @ApiModelProperty(value = "折到位优惠卷")
//    private String encryptParam;










}
