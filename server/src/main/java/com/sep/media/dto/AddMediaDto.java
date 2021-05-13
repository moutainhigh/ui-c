package com.sep.media.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
public class AddMediaDto {
//    @ApiModelProperty(value = "文章标题")
//    private String title;
    @NotNull(message = "请上传图片")
    @ApiModelProperty(value = "宣传图主图")
    private String surfaceImgUrl;
    @ApiModelProperty(value = "用户ID")
    private String token;
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "分类Id")
    private Integer mediaClassifyId;


    @ApiModelProperty(value = "上架下架状态:1上架，-1下架")
    private Integer upDownStatus;

    @ApiModelProperty(value = "是否推荐：1推荐，-1不推荐")
    private Integer isRecommend;
}
