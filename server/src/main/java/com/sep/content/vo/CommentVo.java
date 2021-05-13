package com.sep.content.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVo {


    @ApiModelProperty(value = "评论表Id")
    private Integer id;

    @ApiModelProperty(value = "评论的用户Id")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatarurl;

    @ApiModelProperty(value = "评论类型：1资讯评论,")
    private Integer objType;

    @ApiModelProperty(value = "评论类型相对应的Id")
    private Integer objId;

    @ApiModelProperty(value = "评论状态：1显示,-1不显示")
    private Integer status;

    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    @ApiModelProperty(value = "是否点赞")
    private Integer isPraise;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "评论点赞数")
    private Integer commentPraiseNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;



}
