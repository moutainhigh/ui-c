package com.sep.distribution.vo.background;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value="二级粉丝对象", description="二级粉丝对象")
public class SecondLevelFansVo {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "真实姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "分销身份")
    private String distributionIdentity;

    @ApiModelProperty(value = "累计消费金额")
    private BigDecimal addUpExpense;

}