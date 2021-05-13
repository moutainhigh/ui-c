package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 查询推荐商品
 *
 * @author zhangkai
 * @date 2019年12月28日 23:25
 */
@Data
public class SearchRecommendSkuDto {

    @ApiModelProperty(value = "推荐类型,1:首页推荐，2：热销推荐，3：主打推荐")
    private Integer recommendType;

    @ApiModelProperty(value = "商品状态，0：待上架，1：已上架，2：已售罄")
    private Integer status;
}
