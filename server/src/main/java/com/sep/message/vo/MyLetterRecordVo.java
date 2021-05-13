package com.sep.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "我的信件记录分页查询返回对象", description = "我的信件记录分页查询返回对象")
public class MyLetterRecordVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "通知类型")
    private Integer triggerType;

    @ApiModelProperty(value = "是否已读")
    private boolean isRead;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

}