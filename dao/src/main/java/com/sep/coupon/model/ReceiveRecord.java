package com.sep.coupon.model;

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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 账户表
 * </p>
 *
 * @author litao
 * @since 2020-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_coupon_receive_record")
@ApiModel(value = "ReceiveRecord对象", description = "账户表")
public class ReceiveRecord extends Model<ReceiveRecord> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间 ")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人用户名")
    private String createUid;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人用户名")
    private String updateUid;

    @ApiModelProperty(value = "是否删除，0：未删除，1：已删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "优惠券ID")
    private Integer couponId;

    @ApiModelProperty(value = "是否使用")
    private Integer useStatus;

    @ApiModelProperty(value = "使用时间")
    private LocalDateTime useDateTime;

    @ApiModelProperty(value = "订单ID")
    private Integer orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "实际消费金额")
    private BigDecimal actualMonetary;

    @ApiModelProperty(value = "优惠前消费金额")
    private BigDecimal monetaryTotal;

    @ApiModelProperty(value = "使用开始时间")
    private LocalDate useStartDate;

    @ApiModelProperty(value = "使用结束时间")
    private LocalDate useEndDate;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal monetary;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal cashRebate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
