package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 商品分类字典保存 入参
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Data
@ApiModel(value="商品分类保存参数", description="商品分类保存参数")
public class CategoryDictSaveDto {

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "序号")
    private Integer serial;

    @ApiModelProperty(value = "是否显示，0：否，1：是")
    private int isDisplay;

    @ApiModelProperty(value = "是否推荐，0：否，1：是")
    private int isRecommend;

    @ApiModelProperty(value = "分类图标url")
    private String categoryIcon;

    @ApiModelProperty(value = "分类描述")
    private String categoryDesc;

}
