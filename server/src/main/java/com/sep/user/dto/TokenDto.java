package com.sep.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "接受token入参", description = "接受token入参")
public class TokenDto {

    @ApiModelProperty(value = "token")
    private String token;
}
