package com.sep.user.input;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户查询请求对象", description = "用户查询请求对象")
public class GetUserInput {

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty(value = "手机号码")
    private String telnum;

}
