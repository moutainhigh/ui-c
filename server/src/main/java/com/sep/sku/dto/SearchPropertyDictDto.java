package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 商品属性查询
 *
 * @author zhangkai
 * @date 2019年12月28日 23:25
 */
@Data
public class SearchPropertyDictDto {


    @ApiModelProperty(value = "商品分类ID")
    private Integer categoryId;
}
