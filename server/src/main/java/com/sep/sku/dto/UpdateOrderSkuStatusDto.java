package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 修改订单商品状态
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="修改订单商品状态", description="修改订单商品状态")
public class UpdateOrderSkuStatusDto {

    @ApiModelProperty(value = "订单商品ID集合")
    private List<Integer> ids;

    @ApiModelProperty(value = "状态，0：待支付，1：已支付，2：已取消，3：已退款，4：已完成")
    private int status;

}
