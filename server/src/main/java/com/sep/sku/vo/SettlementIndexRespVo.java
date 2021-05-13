package com.sep.sku.vo;

import com.sep.sku.bean.SettlementSkuInfo;
import com.sep.sku.dto.ZdwCouponDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 确认订单信息
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="确认订单信息", description="确认订单信息")
public class SettlementIndexRespVo {

    @ApiModelProperty("购买商品信息")
    private List<SettlementSkuInfo> buySkuList;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "订单总积分")
    private int totalIntegral;

    @ApiModelProperty("手机号码")
    private List<String> phones;
    /**
     * 所有商品都允许积分兑换 && 可用积分>=商品兑换所需总积分
     */
//    @ApiModelProperty(value = "是否允许积分兑换")
//    private Boolean isIntegralExchange = false;

    /**
     * 所有商品都允许现金支付
     */
//    @ApiModelProperty(value = "是否允许现金支付")
//    private Boolean isCashPay = false;

//    // 用户积分余额
//    private int userRemainIntegral;
//
//    private ZdwCouponDto zdwCouponDto;

}
