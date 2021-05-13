package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 批量修改商品状态
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="批量修改商品状态", description="批量修改商品状态")
public class UpdateSkuStatusDto {

    @ApiModelProperty(value = "ids")
    private List<Integer> ids;

    @ApiModelProperty(value = "状态，1：上架，3：下架")
    private int status;

}
