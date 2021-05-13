package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 批量查询商品
 */
@Data
public class BatchSearchSkuInfoDto implements Serializable{

    @ApiModelProperty("商品ID集合")
    @NotEmpty
    private List<Integer> skuIdList;
}