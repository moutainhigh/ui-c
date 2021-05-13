package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchFriendLinkDto {

    @ApiModelProperty(value = "企业名称")
    private String name;
}
