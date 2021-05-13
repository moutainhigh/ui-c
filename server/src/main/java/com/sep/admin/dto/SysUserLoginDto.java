package com.sep.admin.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;


@Data
@ApiModel(value="系统用户登录", description="系统用户登录")
public class SysUserLoginDto {

    @ApiModelProperty(value = "用户名")
    @NotNull
    private String keyword;

    @ApiModelProperty(value = "密码")
    @NotNull
    private String passWord;
}
