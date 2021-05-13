package com.sep.message.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "我的系统消息分页查询请求对象", description = "我的系统消息分页查询请求对象")
public class MySystemMessageDto extends BaseDto {

    private Integer userId;

    @ApiModelProperty(value = "当前页")
    @NotNull(message = "当前页不能为空")
    private Long currentPage = 0L;

    @ApiModelProperty(value = "每页长度")
    @NotNull(message = "每页长度不能为空")
    private Long pageSize = 10L;

}