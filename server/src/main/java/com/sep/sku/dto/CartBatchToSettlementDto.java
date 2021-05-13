package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.util.List;


/**
 * 购物车批量结算
 *
 * @author zhangkai
 * @date 2019年12月28日 23:25
 */
@Data
public class CartBatchToSettlementDto {

    @ApiModelProperty(value = "skuUniqueKey集合")
    @NotEmpty
    private List<String> skuUniqueKeyList;

    @ApiModelProperty(value = "token")
    @NotNull
    private String token;
}
