package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;


@Data
public class UpdateStatusDto {



    @ApiModelProperty(value = "Id")
    @NotNull(message = "id不能为空")
    private Integer id;

    @ApiModelProperty(value = "状态 1上架。-1下架")
    @NotNull(message = "状态不能为空 ")
    private Integer status;
}
