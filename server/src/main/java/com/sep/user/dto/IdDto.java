package com.sep.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "接受Id入参", description = "接受Id入参")
public class IdDto {

    @ApiModelProperty(value = "id")
    private Integer id;
}
