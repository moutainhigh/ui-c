package com.sep.point.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "增加请求对象", description = "增加请求对象")
public class PointIncreaseInput {

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @ApiModelProperty(value = "积分变动原因")
    @NotNull(message = "积分变动原因不能为空")
    private Integer fundChangeType;

}