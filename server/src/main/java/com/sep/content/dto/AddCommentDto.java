package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddCommentDto {


    @ApiModelProperty(value = "评论的用户Id")
    private String token;

    @ApiModelProperty(value = "评论类型：2资讯评论,4商品")
    private Integer objType;

    @ApiModelProperty(value = "评论类型相对应的Id")
    private Integer objId;

    @ApiModelProperty(value = "评论内容")
    private String commentContent;


}
