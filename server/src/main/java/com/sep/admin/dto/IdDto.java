package com.sep.admin.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;


@Data
public class IdDto {


    @ApiModelProperty(value = "id")
    @NotNull(message = "Id不能为空")
    private Integer id;



}
