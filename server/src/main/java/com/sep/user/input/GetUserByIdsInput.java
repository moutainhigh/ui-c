package com.sep.user.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.util.List;


@Data
@ApiModel(value = "根据ID集合查询用户请求对象", description = "根据ID集合查询用户请求对象")
public class GetUserByIdsInput {

    @ApiModelProperty("用户ID集合")
    @NotNull(message = "用户ID集合不能为空")
    @NotEmpty(message = "用户ID集合不能为空")
    private List<Integer> userIds;

}