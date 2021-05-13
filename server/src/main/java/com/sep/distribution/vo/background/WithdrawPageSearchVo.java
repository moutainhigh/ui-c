package com.sep.distribution.vo.background;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value="提现申请详情对象", description="提现申请详情对象")
public class WithdrawPageSearchVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "提现帐号类型")
    private Integer accountType;

    @ApiModelProperty(value = "提现帐号")
    private String account;

    @ApiModelProperty(value = "开户城市")
    private String openAccountCity;

    @ApiModelProperty(value = "开户行")
    private String bankOfDeposit;

    @ApiModelProperty(value = "申请状态")
    private Integer state;

    @ApiModelProperty(value = "创建时间 ")
    private LocalDateTime createTime;

}