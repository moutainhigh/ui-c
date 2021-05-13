package com.sep.content.dto;

import com.sep.common.utils.PageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchArticleDto extends PageUtil {


    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "用户ID")
    private String token;

    @ApiModelProperty(value = "分类Id")
    private Integer articleClassifyId;


    @ApiModelProperty(value = "上架下架状态:1上架，-1下架")
    private Integer upDownStatus;

    @ApiModelProperty(value = "是否推荐：1推荐，-1不推荐")
    private Integer isRecommend;


}
