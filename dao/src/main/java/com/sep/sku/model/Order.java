package com.sep.sku.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * sku订单表
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order")
@ApiModel(value="Order对象", description="sku订单表")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "销售顾问id")
    private Integer counselorId;

    @ApiModelProperty(value = "收货方式，0：邮寄，1：自取")
    private int takeType;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "微信id")
    private String unionId;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "微信第三方交易单号")
    private String wxTradeNo;

    @ApiModelProperty(value = "优惠前金额")
    private BigDecimal originAmount;

    @ApiModelProperty(value = "优惠券优惠金额")
    private BigDecimal couponDiscountAmount;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "交易积分")
    private int integralNum;

    @ApiModelProperty(value = "状态，0：待支付，1：已完成，2：已取消，3：已退款")
    private int status;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "收货信息id")
    private Integer takeInfoId;

    @ApiModelProperty(value = "支付方式，0：微信，1：积分")
    private int payWay;

    @ApiModelProperty(value = "优惠券id")
    private Integer couponId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;
//
//    @ApiModelProperty(value = "折到位优惠卷ID")
//    private Integer zdwOrderSkuId;

//    @ApiModelProperty(value = "这到位优惠卷说明")
//    private String zdwRemark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
