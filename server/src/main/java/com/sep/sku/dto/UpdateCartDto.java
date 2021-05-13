package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 修改购物车数量参数
 *
 * @author zhangkai
 * @date 2019年12月28日 23:25
 */
@Data
public class UpdateCartDto {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "一个用户的购物车内唯一标识，此标识通过skuId+skuProperty生成")
    private String skuUniqueKey;

    @ApiModelProperty(value = "购买数量")
    private int buyNum = 1;
}
