package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 修改订单状态
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="修改订单状态", description="修改订单状态")
public class UpdateOrderStatusDto {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "状态，0：待支付，1：已支付，2：已取消，3：已退款，4：已完成")
    private int status;

}
