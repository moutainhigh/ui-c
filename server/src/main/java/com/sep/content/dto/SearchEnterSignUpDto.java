package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@Data
public class SearchEnterSignUpDto {

    @ApiModelProperty("活动id")
    @NotNull(message = "活动id不能为空")
    @NotEmpty(message = "活动id不能为空")
    private Integer activeId;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "是否使用：1使用，-1未使用")
    private Integer isApply;
}
