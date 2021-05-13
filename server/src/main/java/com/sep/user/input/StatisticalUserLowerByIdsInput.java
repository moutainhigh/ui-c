package com.sep.user.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.util.List;


@Data
@ApiModel(value = "用户下级统计请求对象", description = "用户下级统计请求对象")
public class StatisticalUserLowerByIdsInput {

    @ApiModelProperty("用户ID集合")
    @NotNull(message = "用户ID集合不能为空")
    @NotEmpty(message = "用户ID集合不能为空")
    private List<Integer> userIds;

    @ApiModelProperty("是否统计一级粉丝")
    private boolean isCountLower1 = true;

    @ApiModelProperty("是否统计二级粉丝")
    private boolean isCountLower2 = true;

}