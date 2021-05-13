package com.sep.message.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "发送信件对象", description = "发送信件对象")
public class SendLetterInput {

    @ApiModelProperty(value = "用户ID集合")
    @NotNull(message = "用户ID集合不能为空")
    @Size(min = 1, message = "用户ID集合不能为空")
    private List<Integer> userIds;

    @ApiModelProperty(value = "触发点")
    @NotNull(message = "触发点不能为空")
    private Integer triggerPoint;

    @ApiModelProperty(value = "更新人用户名")
    @NotNull(message = "更新人用户名不能为空")
    private Map<String, Object> messages;

}