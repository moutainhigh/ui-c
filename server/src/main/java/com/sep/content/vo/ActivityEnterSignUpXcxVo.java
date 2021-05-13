package com.sep.content.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ActivityEnterSignUpXcxVo {
    @ApiModelProperty(value = "活动Id")
    private Integer activityId;
    @ApiModelProperty(value = "活动标题")
    private String activityName;
    @ApiModelProperty("填写项信息")
    List<Map<String,String>> list;
}
