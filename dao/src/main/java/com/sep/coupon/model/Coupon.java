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
 * 优惠卷表
 * </p>
 *
 * @author litao
 * @since 2020-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_coupon_coupon")
@ApiModel(value="Coupon对象", description="优惠卷表")
public class Coupon extends Model<Coupon> {

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

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "优惠券类型")
    private Integer type;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal monetary;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal cashRebate;

    @ApiModelProperty(value = "总数")
    private Integer total;

    @ApiModelProperty(value = "库存")
    private Integer inventory;

    @ApiModelProperty(value = "已使用量")
    private Integer used;

    @ApiModelProperty(value = "领取开始时间")
    private LocalDate receiveStartDate;

    @ApiModelProperty(value = "领取结束时间")
    private LocalDate receiveEndDate;

    @ApiModelProperty(value = "使用开始时间")
    private LocalDate useStartDate;

    @ApiModelProperty(value = "使用结束时间")
    private LocalDate useEndDate;

    @ApiModelProperty(value = "使用范围")
    private Integer useScope;

    @ApiModelProperty(value = "优惠共享类型")
    private Integer discountsShareType;

    @ApiModelProperty(value = "发行状态")
    private Integer publishStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
