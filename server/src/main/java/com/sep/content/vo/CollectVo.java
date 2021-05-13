package com.sep.content.vo;


import com.sep.sku.vo.SkuInfoRespVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CollectVo {

    @ApiModelProperty(value = "收藏Id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "收藏类型：1商品，2资讯")
    private Integer objType;

    @ApiModelProperty(value = "类型对应Id")
    private Integer objId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "关联的商品")
    private SkuInfoRespVo skus;

    @ApiModelProperty(value = "关联的资讯")
    private ArticleVo articleVo;
}
