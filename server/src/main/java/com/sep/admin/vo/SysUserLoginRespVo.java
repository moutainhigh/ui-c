package com.sep.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "员工登录返回信息")
public class SysUserLoginRespVo{

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "权限id集合")
    private List<Integer> permissionIdList;

}
