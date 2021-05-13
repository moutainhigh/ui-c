package com.sep.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;


@Data
public class UpdateArticleClassifySortDto {


    @ApiModelProperty(value = "资讯分类Id")
    @NotNull(message = "ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "排序位置 ")
    @NotNull(message = "排序位置不能为空")
    private Integer sort;

}
