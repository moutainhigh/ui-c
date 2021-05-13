package com.sep.message.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "阅读系统消息请求对象", description = "阅读系统消息请求对象")
public class ReadMessageDto extends BaseDto {

    @ApiModelProperty(value = "消息ID")
    @NotNull(message = "消息ID不能为空")
    private Integer id;

    private Integer userId;

}