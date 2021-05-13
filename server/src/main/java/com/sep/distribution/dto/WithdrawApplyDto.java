package com.sep.distribution.dto;

import com.sep.distribution.enums.WithdrawAccountType;
import com.sep.common.oval.MemberOfEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "提现申请请求对象", description = "提现申请请求对象")
public class WithdrawApplyDto extends BaseDto{

    private Integer userId;

    @ApiModelProperty(value = "提现金额")
    @NotNull(message = "提现金额不能为空")
    @Min(value = 0, message = "提现金额不能为负数")
    private BigDecimal amount;

    @ApiModelProperty(value = "提现帐号类型")
    @NotNull(message = "提现帐号类型不能为空")
    @MemberOfEnum(clazz = WithdrawAccountType.class)
    private Integer accountType;

    @ApiModelProperty(value = "姓名")
    @NotNull(message = "姓名不能为空")
    @NotEmpty(message = "姓名不能为空")
    @Length(max = 20, message = "姓名长度不能超过20个字符")
    private String name;

    @ApiModelProperty(value = "提现帐号")
    @NotNull(message = "提现帐号不能为空")
    @NotEmpty(message = "提现帐号不能为空")
    @Length(max = 20, message = "提现帐号长度不能超过20个字符")
    private String account;

    @ApiModelProperty(value = "开户城市")
    private String openAccountCity;

    @ApiModelProperty(value = "开户行")
    private String bankOfDeposit;

}