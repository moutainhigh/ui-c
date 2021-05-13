package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 去支付订单参数
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="去支付订单", description="去支付订单")
public class GotoPayDto {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("token")
    private String token;

}
