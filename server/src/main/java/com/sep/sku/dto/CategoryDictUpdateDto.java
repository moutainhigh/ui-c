package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

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
public class CategoryDictUpdateDto extends CategoryDictSaveDto{

    @ApiModelProperty(value = "id")
    @NotNull
    private Integer id;

}
