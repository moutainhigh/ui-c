package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;


@Data
public class AddRetransmissionDto {


    @ApiModelProperty(value = "用户Id")
    @NotBlank(message = "用户不能为空")
    private String token;

    @ApiModelProperty(value = "转发类型：1活动，2资讯")
    @NotNull(message = "转发类型不能为空")
    private Integer objType;

    @ApiModelProperty(value = "类型对应Id")
    @NotNull(message = "id不能为空")
    private Integer objId;
}
