package com.sep.sku.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 购物车计价接口返回
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="购物车计价结果", description="购物车计价结果")
public class CartValuationRespVo {

    @ApiModelProperty(value = "商品总金额")
    private BigDecimal totalSkuAmount = BigDecimal.ZERO;

}
