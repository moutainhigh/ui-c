package com.sep.content.vo;


import com.sep.sku.vo.SkuInfoRespVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleVo {


    @ApiModelProperty(value = "文章Id(新闻资讯)")
    private Integer id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章描述")
    private String remark;

    @ApiModelProperty(value = "分类Id")
    private Integer articleClassifyId;

    @ApiModelProperty(value = "分类名称")
    private String articleClassifyName;

    @ApiModelProperty(value = "点赞数量")
    private Integer praiseNum;

    @ApiModelProperty(value = "宣传图主图")
    private String publicityImgUrl;

    @ApiModelProperty(value = "封面图片URL")
    private String surfaceImgUrl;

    @ApiModelProperty(value = "视频URL")
    private String videoUrl;

    @ApiModelProperty(value = "上架下架状态:1上架，-1下架")
    private Integer upDownStatus;

    @ApiModelProperty(value = "是否推荐：1推荐，-1不推荐")
    private Integer isRecommend;

    @ApiModelProperty(value = "推荐时间")
    private LocalDateTime recommendTime;

    @ApiModelProperty(value = "文章内容HTML")
    private String articleContent;

    @ApiModelProperty(value = "关联的商品Id,用  ,  分割")
    private String skuIds;

    @ApiModelProperty(value = "评论数")
    private Integer commentNum;

    @ApiModelProperty(value = "是否收藏")
    private Integer isCollec;

    @ApiModelProperty(value = "资讯的收藏数量")
    private Integer collecNum;

    @ApiModelProperty(value = "转发数")
    private Integer retransmissionNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "关联商品")
    List<SkuInfoRespVo> skus;



}
