package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * SKU查询体
 *
 * @author zhangkai
 * @date 2019年12月28日 23:25
 */
@Data
public class SearchSkuDto {


    @ApiModelProperty(value = "商品ID")
    private Integer id;

    @ApiModelProperty(value = "店铺id")
    private Integer shopId;

    @ApiModelProperty(value = "商品分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "商品状态，0：待上架，1：已上架，2：已售罄，3：已下架，4：已结束")
    private Integer status;

    @ApiModelProperty(value = "当前页")
    private Integer currentPage = 0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize = 20;
}
