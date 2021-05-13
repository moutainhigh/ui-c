package com.sep.message.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@Data
@ApiModel(value = "系统消息更新请求对象", description = "系统消息更新请求对象")
public class UpdateSystemMessageDto extends BaseUpdateDto {

    @ApiModelProperty(value = "标题")
    @NotNull(message = "标题不能为空")
    @NotEmpty(message = "标题不能为空")
    @Length(max = 20, message = "标题长度不能超过20个字符")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

}