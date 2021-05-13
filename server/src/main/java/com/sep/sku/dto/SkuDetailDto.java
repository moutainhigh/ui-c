package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

/**
 * <p>
 * 小程序端商品详情页参数
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="小程序端商品详情页参数", description="小程序端商品详情页参数")
public class SkuDetailDto {

    @ApiModelProperty(value = "ID")
    @NotNull
    private Integer id;

    @ApiModelProperty("token")
    @NotNull
    private String token;

}
