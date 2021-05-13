package com.sep.content.dto;

import com.sep.common.utils.PageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchActivityDto extends PageUtil {

    @ApiModelProperty(value = "活动标题")
    private String title;

    @ApiModelProperty(value = "活动状态:1未开始，2进行中，3已完成")
    private Integer status;

    private String token;
}
