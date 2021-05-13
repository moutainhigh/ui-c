package com.sep.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "信件模板分页查询返回对象", description = "信件模板分页查询返回对象")
public class LetterTemplateVo {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "是否开启")
    private Integer enabledStatus;

    @ApiModelProperty(value = "触发点")
    private Integer triggerPoint;

    @ApiModelProperty(value = "通知类型")
    private Integer triggerType;

}