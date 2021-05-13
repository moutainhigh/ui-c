package com.sep.message.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "基础请求对象", description = "基础请求对象")
public class BaseDto {

    @ApiModelProperty("token")
    @NotNull(message = "token不能为空")
    @NotEmpty(message = "token不能为空")
    private String token;

}