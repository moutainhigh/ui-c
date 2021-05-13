package com.sep.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "授权用户信息", description = "授权用户信息")
public class AuthorizeUserInfoDto {

    @ApiModelProperty(value = "encryptedData")
    private String encryptedData;

    @ApiModelProperty(value = "iv")
    private String iv;

    @ApiModelProperty(value = "token")
    private String token;




}
