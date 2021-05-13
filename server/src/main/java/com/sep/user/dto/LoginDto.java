package com.sep.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户登录code", description = "用户登录code")
public class LoginDto {

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "邀请人用户id")
    private Integer inviteParentId;

    @ApiModelProperty(value = "用户手机号码")
    private String telnum;


}
