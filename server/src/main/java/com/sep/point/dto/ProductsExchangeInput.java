package com.sep.point.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "兑换商品请求对象", description = "兑换商品请求对象")
public class ProductsExchangeInput {

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @ApiModelProperty(value = "花费积分")
    @NotNull(message = "花费积分不能为空")
    private Integer takePoint;

    @ApiModelProperty(value = "订单ID")
    @NotNull(message = "订单ID不能为空")
    private Integer orderId;

    @ApiModelProperty(value = "订单号")
    @NotNull(message = "订单号不能为空")
    @NotEmpty(message = "订单号不能为空")
    @Length(max = 64, message = "订单号长度不能超过64个字符")
    private String orderNo;

}