package com.sep.sku.vo;


import com.sep.sku.vo.ServerSearchOrderSkuRespVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 统计订单信息
 *
 * @author zhangkai
 * @date 2019年12月14日 22:52
 */
@Data
public class ServerSearchOrderRespVo {

    @ApiModelProperty(value = "支付类型名称")
    private String payWayName;

    @ApiModelProperty(value = "订单状态名称")
    private String statusName;

    @ApiModelProperty(value = "购买商品总数量")
    private int skuTotalCount;

    @ApiModelProperty(value = "商品集合")
    private List<ServerSearchOrderSkuRespVo> orderSkuList;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

//    @ApiModelProperty(value = "销售顾问id")
//    private Integer counselorId;

//    @ApiModelProperty(value = "收货方式，0：邮寄，1：自取")
//    private int takeType;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "微信id")
    private String unionId;

    @ApiModelProperty(value = "微信第三方交易单号")
    private String wxTradeNo;
//
//    @ApiModelProperty(value = "优惠前金额")
//    private BigDecimal originAmount;

//    @ApiModelProperty(value = "优惠券优惠金额")
//    private BigDecimal couponDiscountAmount;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;
//
//    @ApiModelProperty(value = "交易积分")
//    private int integralNum;

    @ApiModelProperty(value = "状态，0：待支付，1：已支付，2：已取消，3：已退款，4：已完成")
    private int status;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "收货信息id")
    private Integer takeInfoId;
//
//    @ApiModelProperty(value = "支付方式，0：微信，1：积分")
//    private int payWay;

//    @ApiModelProperty(value = "优惠券id")
//    private Integer couponId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "订单状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "状态，0：待付款，1：待卖家发货，2：部分发货，3：待收货，4：已完成，5：交易关闭，6：已退款")
    private int statusCode;


}
