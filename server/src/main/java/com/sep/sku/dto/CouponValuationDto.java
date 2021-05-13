package com.sep.sku.dto;

import com.sep.sku.bean.CouponValuationSkuInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 优惠券计价 参数
 */
@Data
@ApiModel(value="优惠券计价参数", description="优惠券计价参数")
public class CouponValuationDto {

    @ApiModelProperty("购买商品信息")
    private List<CouponValuationSkuInfo> buySkuList;

//    @ApiModelProperty("选择的优惠券id")
//    private Integer couponId;
}