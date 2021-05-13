package com.sep.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "我的系统消息详情对象", description = "我的系统消息详情对象")
public class MySystemMessageVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "是否已读")
    private boolean isRead;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

}