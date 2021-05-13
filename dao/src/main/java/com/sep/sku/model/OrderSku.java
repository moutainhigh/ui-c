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
 * 订单sku信息
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order_sku")
@ApiModel(value="OrderSku对象", description="订单sku信息")
public class OrderSku extends Model<OrderSku> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "支付方式，0：微信，1：积分")
    private int payWay;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "微信id")
    private String unionId;

    @ApiModelProperty(value = "状态，0：待支付，1：已支付，2：已取消，3：已退款，4：已完成")
    private int status;

    @ApiModelProperty(value = "评价状态:0:未评价,1:已评价")
    private int evaluateStatus;

    @ApiModelProperty(value = "邮寄状态，0：待邮寄，1：已邮寄，2：已收货")
    private int expressStatus;

    @ApiModelProperty(value = "自提状态，0：待自提，1：已自提")
    private int fetchStatus;

    @ApiModelProperty(value = "发货时间")
    private LocalDateTime expressTime;

    @ApiModelProperty(value = "自提时间")
    private LocalDateTime fetchTime;

    @ApiModelProperty(value = "快递公司id")
    private int expressType;

    @ApiModelProperty(value = "快递单号")
    private String expressNo;

    @ApiModelProperty(value = "店铺id")
    private Integer shopId;

    @ApiModelProperty(value = "商品id")
    private Integer skuId;

    @ApiModelProperty(value = "购买数量")
    private Integer buyNum;

    @ApiModelProperty(value = "sku成交价")
    private BigDecimal skuPrice;

    @ApiModelProperty(value = "sku原价")
    private BigDecimal originPrice;

    @ApiModelProperty(value = "sku兑换所需积分")
    private int skuIntegral;

    @ApiModelProperty(value = "消费码")
    private String consumableCode;

//    @ApiModelProperty(value = "折到位优惠卷ID")
//    private Integer zdwOrderSkuId;

//    @ApiModelProperty(value = "这到位优惠卷说明")
//    private String zdwRemark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
