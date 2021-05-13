package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;
import java.util.List;


@Data
public class AddEnterDto {


    @ApiModelProperty(value = "活动Id")
    @NotNull(message = "活动ID不能为空")
    private Integer activityId;

    @ApiModelProperty(value = "token")
    @NotBlank(message = "用户信息不能为空")
    private String token;
    @ApiModelProperty("ip")
    private String ip;
    @ApiModelProperty(value = "用户报名信息")
    private List<ActivityEnterSignUpDto> activityEnterSignUpDtos;

    @ApiModelProperty(value = "是否使用：1使用，-1未使用")
    @NotNull(message = "请选择")
    private Integer isApply;
    @ApiModelProperty(value = "费用")
    private BigDecimal cost;

}
