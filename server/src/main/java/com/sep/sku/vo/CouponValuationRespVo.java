package com.sep.sku.vo;

import com.sep.sku.bean.CouponValuationRespSkuInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 优惠券计价返回信息
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="优惠券计价返回信息", description="优惠券计价返回信息")
public class CouponValuationRespVo {

    @ApiModelProperty("购买商品计价返回信息")
    private List<CouponValuationRespSkuInfo> buySkuList;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalAmount;

}
