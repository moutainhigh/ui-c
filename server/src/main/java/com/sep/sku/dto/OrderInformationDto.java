package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
public class OrderInformationDto {
    @NotNull
    @ApiModelProperty("商品id")
    private Integer id;
    @NotNull
    @ApiModelProperty("手机号码")
    private String phone;
}
