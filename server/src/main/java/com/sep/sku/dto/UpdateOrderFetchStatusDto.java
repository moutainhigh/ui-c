package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 修改商品订单自提状态
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="修改商品订单自提状态", description="修改商品订单自提状态")
public class UpdateOrderFetchStatusDto {

    @ApiModelProperty(value = "ids")
    private List<Integer> ids;

    @ApiModelProperty(value = "状态，0：待自提，1：已自提")
    private int fetchStatus;

}
