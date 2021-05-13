package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 进入结算页商品信息
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="进入结算页商品信息", description="进入结算页商品信息")
public class SettlementSkuDto extends SettlementQuickBuySkuDto{

    @ApiModelProperty(value = "一个用户的购物车内唯一标识，此标识通过skuId+skuProperty生成")
    private String skuUniqueKey;

}
