package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;


@Data
public class CollectDto {


    @ApiModelProperty(value = "用户信息")
    @NotBlank(message = "用户信息不能为空")
    private String token;

    @ApiModelProperty(value = "收藏类型：1商品，2资讯")
    @NotNull(message = "收藏类型不能为空")
    private Integer objType;

    @ApiModelProperty(value = "类型对应Id")
    @NotNull(message = "收藏Id不能为空")
    private Integer objId;
}
