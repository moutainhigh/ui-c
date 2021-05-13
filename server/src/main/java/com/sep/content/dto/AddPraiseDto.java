package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;


@Data
public class AddPraiseDto {

    @ApiModelProperty(value = "点赞的用户Id")
    @NotBlank(message = "用户信息不能为空")
    private String token;

    @ApiModelProperty(value = "点赞类型:1活动，2资讯，3评论；")
    @NotNull(message = "点赞类型不能为空")
    private Integer objType;

    @ApiModelProperty(value = "类型对应的id")
    @NotNull(message = "点赞ID不能为空")
    private Integer objId;

}
