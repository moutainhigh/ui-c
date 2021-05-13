package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 修改商品订单发货状态
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="批量修改商品订单发货状态", description="批量修改商品订单发货状态")
public class UpdateOrderExpressStatusDto {

    @ApiModelProperty(value = "ids")
    private List<Integer> ids;

    @ApiModelProperty(value = "快递公司id")
    private int expressType;

    @ApiModelProperty(value = "快递单号")
    private String expressNo;

    @ApiModelProperty(value = "状态，0：待发货，1：已发货，2：已收货")
    private int expressStatus;

}
