package com.sep.message.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;

import java.util.List;

@Data
@ApiModel(value = "发布系统消息请求对象", description = "发布系统消息请求对象")
public class PublishSystemMessageDto {

    @ApiModelProperty(value = "主键")
    @NotNull(message = "ID集合不能为空")
    @Size(min = 1, message = "ID集合不能为空")
    private List<Integer> ids;

    @ApiModelProperty(value = "更新人用户名")
    @NotNull(message = "更新人用户名不能为空")
    @NotEmpty(message = "更新人用户名不能为空")
    @Length(max = 20, message = "更新人用户名长度不能超过20个字符")
    private String updateUid;

}