package com.sep.content.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ActivityEnterSignUpVo {

    @ApiModelProperty(value = "活动Id")
    private Integer activityId;
    @ApiModelProperty(value = "活动标题")
    private String activityName;
    @ApiModelProperty("填写项信息")
    List<Map<Integer,String>> list;
//    @ApiModelProperty(value = "活动填写项id")
//    private Integer signId;
//    @ApiModelProperty(value = "用户id")
//    private Integer userId;
//    @ApiModelProperty(value = "用户报名信息")
//    private String information;
//    @ApiModelProperty(value = "是否使用：1使用，-1未使用")
//    private Integer isApply;
}
