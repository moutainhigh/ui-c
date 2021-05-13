package com.sep.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "是否授权入参", description = "是否授权入参")
public class IsAuthDto {

    @ApiModelProperty(value = "token")
    private String token;
}
