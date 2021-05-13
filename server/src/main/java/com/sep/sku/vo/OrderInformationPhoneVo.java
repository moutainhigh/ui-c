package com.sep.sku.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderInformationPhoneVo {
    private String phone;
    @ApiModelProperty("0：未使用，1:已使用")
    private Integer status;
}
