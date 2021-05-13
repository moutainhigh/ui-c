package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

/**
 * 生成商品分享二维码参数
 *
 * @author zhangkai
 * @date 2020年02月23日 14:33
 */
@Data
@ApiModel(value="生成商品分享二维码参数", description="生成商品分享二维码参数")
public class GenerateShareQrCodeDto {

    @ApiModelProperty(value = "token")
    @NotNull
    private String token;

    @ApiModelProperty(value = "商品id")
    @NotNull
    private Integer skuId;
}
