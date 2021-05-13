package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;


@Data
public class AddArticleDto {


    @ApiModelProperty(value = "文章Id(新闻资讯)")
    private Integer id;

    @ApiModelProperty(value = "文章标题")
    @NotBlank(message = "资讯标题不能为空")
    private String title;

    @ApiModelProperty(value = "文章描述")
    private String remark;

    @ApiModelProperty(value = "分类Id")
    @NotNull(message = "请选择分类")
    private Integer articleClassifyId;

    @ApiModelProperty(value = "点赞数量")
    private Integer praiseNum;

    @ApiModelProperty(value = "宣传图主图")
    private String publicityImgUrl;

    @ApiModelProperty(value = "封面图片URL")
    @NotBlank(message = "封面图片URL不能为空")
    private String surfaceImgUrl;

    @ApiModelProperty(value = "视频URL")
    private String videoUrl;

    @ApiModelProperty(value = "上架下架状态:1上架，-1下架")
    private Integer upDownStatus;

    @ApiModelProperty(value = "是否推荐：1推荐，-1不推荐")
    private Integer isRecommend;

    @ApiModelProperty(value = "文章内容HTML")
    @NotBlank(message = " 文章内容不能为空")
    private String articleContent;

    @ApiModelProperty(value = "关联的商品Id,用  ,  分割")
    private String skuIds;

    @ApiModelProperty(value = "评论数")
    private Integer commentNum;


}
