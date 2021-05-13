package com.sep.distribution.vo.xcx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "分销订单VO", description = "分销订单VO")
public class DistributionOrderVo {

    @ApiModelProperty("支付类型名称")
    private String payWayName;
    @ApiModelProperty("订单状态名称")
    private String statusName;
    @ApiModelProperty("订单状态描述")
    private String statusDesc;
    @ApiModelProperty("购买商品总数量")
    private int skuTotalCount;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("用户id")
    private Integer userId;
//    @ApiModelProperty("优惠前金额")
//    private BigDecimal originAmount;
//    @ApiModelProperty("优惠券优惠金额")
//    private BigDecimal couponDiscountAmount;
    @ApiModelProperty("交易金额")
    private BigDecimal amount;
    @ApiModelProperty("交易积分")
    private int integralNum;
    @ApiModelProperty("状态，0：待支付，1：已支付，2：已取消，3：已退款，4：已完成")
    private int status;
    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;
    @ApiModelProperty("支付方式，0：微信，1：积分")
    private int payWay;
    @ApiModelProperty("下单时间")
    private LocalDateTime createTime;
    @ApiModelProperty("粉丝级别标识，0：一级，1：二级")
    private Integer fansRank;
    @ApiModelProperty("商品集合")
    private List<DistributionOrderSkuVo> skus;

}