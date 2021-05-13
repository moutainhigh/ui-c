package com.sep.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "信件记录分页查询返回对象", description = "信件记录分页查询返回对象")
public class LetterRecordVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "是否阅读")
    private Integer readStatus;

    @ApiModelProperty(value = "触发点")
    private Integer triggerPoint;

    @ApiModelProperty(value = "触发点类型")
    private Integer triggerType;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "阅读时间")
    private LocalDateTime updateTime;

}