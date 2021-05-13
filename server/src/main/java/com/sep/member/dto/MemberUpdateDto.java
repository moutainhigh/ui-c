package com.sep.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberUpdateDto {
    private Integer id;
    @ApiModelProperty("")
    private Integer state;
}
