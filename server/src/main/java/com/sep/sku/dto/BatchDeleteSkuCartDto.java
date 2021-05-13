package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 批量删除购物车产品
 *
 * @author zhangkai
 * @date 2019年12月28日 23:25
 */
@Data
public class BatchDeleteSkuCartDto {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "一个用户的购物车内唯一标识集合，此标识通过skuId+skuProperty生成")
    private List<String> skuUniqueKeyList;
}
