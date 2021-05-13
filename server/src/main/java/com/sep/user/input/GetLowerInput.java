package com.sep.user.input;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

import java.util.List;

@Data
@ApiModel(value = "获取粉丝请求对象", description = "获取粉丝请求对象")
public class GetLowerInput {

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @ApiModelProperty("粉丝ID集合")
    private List<Integer> lowerIds;

    @ApiModelProperty("粉丝手机号码")
    private String lowerTelnum;

    @ApiModelProperty(value = "当前页")
    private Long currentPage = 0L;

    @ApiModelProperty(value = "每页长度")
    private Long pageSize = 10L;

}