package com.sep.sku.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 首页订单统计返回信息
 *
 * @author zhangkai
 * @date 2019年12月14日 22:52
 */
@Data
@ApiModel(value = "首页订单统计返回信息", description = "首页订单统计返回信息")
public class HomeOrderStatisticalRespVo {

    @ApiModelProperty("商品数")
    private int skuCount;

    @ApiModelProperty(value = "订单数")
    private int orderCount;

    @ApiModelProperty(value = "支付总额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "每日订单量统计")
    private Map<String,Integer> monthOrderCountMap;

}
