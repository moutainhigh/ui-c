package com.sep.sku.dto;

import com.sep.sku.bean.SettlementPropertyInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 立即购买确认订单参数
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="立即购买确认订单参数", description="立即购买确认订单参数")
public class SettlementQuickBuySkuDto {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "商品ID")
    private Integer skuId;

    @ApiModelProperty(value = "购买数量")
    private int buyNum = 1;
//
//    @ApiModelProperty(value = "商品属性集合")
//    private List<SettlementPropertyInfo> skuPropertyInfoList;
//
//    @ApiModelProperty(value = "折到位优惠卷")
//    private String encryptParam;
    @ApiModelProperty("手机号码")
    private List<String> phones;



}
