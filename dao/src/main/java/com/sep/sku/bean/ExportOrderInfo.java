package com.sep.sku.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author zhangkai
 * @date 2019年12月14日 22:52
 */
@Data
public class ExportOrderInfo {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "下单时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "买家id")
    private Integer userId;

    @ApiModelProperty(value = "支付方式名称")
    private String payway;

    @ApiModelProperty(value = "订单金额")
    private String orderAmount;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "订单商品，用','隔开")
    private String orderSkus;


}
