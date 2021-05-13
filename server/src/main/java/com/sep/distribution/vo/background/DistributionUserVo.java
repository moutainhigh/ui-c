package com.sep.distribution.vo.background;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value="分销用户对象", description="分销用户对象")
public class DistributionUserVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "分销身份")
    private String distributionIdentity;

    @ApiModelProperty(value = "一级粉丝数")
    private Integer stairFansCount;

    @ApiModelProperty(value = "二级粉丝数")
    private Integer secondLevelFansCount;

    @ApiModelProperty(value = "累计返现金额")
    private BigDecimal addUpCashBack;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "累计消费金额")
    private BigDecimal addUpExpense;

    @ApiModelProperty(value = "邀请人")
    private Integer inviterId;

    @ApiModelProperty(value = "二维码")
    private String quickMark;

}