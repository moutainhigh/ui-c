package com.sep.content.dto;

import com.sep.common.utils.PageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchCommentDto extends PageUtil {

    @ApiModelProperty(value = "用户Id ")
    private Integer userId;

    @ApiModelProperty(value = "当前登录用户")
    private String token;

    @ApiModelProperty(value = "评论类型：2资讯评论,4商品")
    private Integer objType;

    @ApiModelProperty(value = "评论类型相对应的Id")
    private Integer objId;

    @ApiModelProperty(value = "评论状态：1显示,-1不显示")
    private Integer status;

}
