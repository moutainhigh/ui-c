package com.sep.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel(value = "优惠卷添加请求对象", description = "优惠卷添加请求对象")
public class CouponAddDto {

    @ApiModelProperty(value = "创建人用户名")
    @NotNull(message = "创建人用户名不能为空")
    @NotEmpty(message = "创建人用户名不能为空")
    @Length(max = 20, message = "创建人用户名长度不能超过20个字符")
    private String createUid;

    @ApiModelProperty(value = "标题")
    @Length(max = 20, message = "标题长度不能超过20个字符")
    private String title;

    @ApiModelProperty(value = "优惠券类型")
    @NotNull(message = "优惠券类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "消费金额")
    @NotNull(message = "消费金额不能为空")
    @Min(value = 0, message = "消费金额不能为负数")
    private BigDecimal monetary;

    @ApiModelProperty(value = "优惠金额")
    @NotNull(message = "优惠金额不能为空")
    @Min(value = 0, message = "优惠金额不能为负数")
    private BigDecimal cashRebate;

    @ApiModelProperty(value = "总量")
    @NotNull(message = "总量不能为空")
    @Min(value = 0, message = "总量不能为负数")
    private Integer total;

    @ApiModelProperty(value = "领取有效期开始时间")
    @NotNull(message = "领取有效期开始时间不能为空")
    private LocalDate receiveStartDate;

    @ApiModelProperty(value = "领取有效期结束时间")
    @NotNull(message = "领取有效期结束时间不能为空")
    private LocalDate receiveEndDate;

    @ApiModelProperty(value = "使用有效期开始时间")
    @NotNull(message = "使用有效期开始时间不能为空")
    private LocalDate useStartDate;

    @ApiModelProperty(value = "使用有效期结束时间")
    @NotNull(message = "使用有效期结束时间不能为空")
    private LocalDate useEndDate;

    @ApiModelProperty(value = "使用范围")
    @NotNull(message = "使用范围不能为空")
    private Integer useScope;

    @ApiModelProperty(value = "优惠共享类型")
    @NotNull(message = "优惠共享不能为空")
    private Integer discountsShareType;

}