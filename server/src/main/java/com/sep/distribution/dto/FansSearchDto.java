package com.sep.distribution.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.oval.constraint.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "粉丝查询请求对象", description = "粉丝查询请求对象")
public class FansSearchDto extends BaseDto {

    private Integer userId;

    @ApiModelProperty(value = "当前页")
    @NotNull(message = "当前页不能为空")
    private Long currentPage = 0L;

    @ApiModelProperty(value = "每页长度")
    @NotNull(message = "每页长度不能为空")
    private Long pageSize = 10L;

}