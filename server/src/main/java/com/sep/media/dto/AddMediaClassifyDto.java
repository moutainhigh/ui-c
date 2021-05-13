package com.sep.media.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotBlank;

@Data
public class AddMediaClassifyDto {
    @ApiModelProperty(value = "合伙人分类Id")
    private Integer id;

    @ApiModelProperty(value = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String classifyName;

    @ApiModelProperty(value = "分类图标")
    private String imgUrl;

    @ApiModelProperty(value = "分类排序")
    private Integer sort;

    @ApiModelProperty(value = "上架下架状态:1上架，-1下架")
    private Integer upDownStatus;
}
