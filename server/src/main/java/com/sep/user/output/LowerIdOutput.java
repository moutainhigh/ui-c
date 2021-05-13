package com.sep.user.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LowerIdOutput {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private Integer id;

    @ApiModelProperty("粉丝级别标识，0：一级，1：二级")
    private Integer fansRank;

}