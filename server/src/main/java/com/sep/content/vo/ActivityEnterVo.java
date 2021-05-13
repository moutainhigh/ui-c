package com.sep.content.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityEnterVo {



    @ApiModelProperty(value = "活动报名Id")
    private Integer id;

    @ApiModelProperty(value = "活动Id")
    private Integer activityId;

    @ApiModelProperty(value = "活动标题")
    private String activityName;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "报名人姓名")
    private String name;

    @ApiModelProperty(value = "报名人 手机号")
    private String phone;

    @ApiModelProperty(value = "报名人年龄")
    private Integer age;

    @ApiModelProperty(value = "是否使用：1使用，-1未使用")
    private Integer isApply;

    @ApiModelProperty(value = "逻辑删除:1已删除,0未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime ts;


}
