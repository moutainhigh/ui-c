package com.sep.content.dto;

import com.sep.common.utils.PageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchEnterDto extends PageUtil {


    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "活动标题")
    private String activityName;

    @ApiModelProperty(value = "是否使用：1使用，-1未使用")
    private Integer isApply;


}
