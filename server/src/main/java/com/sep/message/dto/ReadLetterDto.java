package com.sep.message.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "阅读信件请求对象", description = "阅读信件请求对象")
public class ReadLetterDto extends BaseDto {

    @ApiModelProperty(value = "信件ID")
    @NotNull(message = "信件ID不能为空")
    private Integer id;

    private Integer userId;

}