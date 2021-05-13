package com.sep.distribution.vo.background;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class InvoiceVo {
    @ApiModelProperty("id")
    private Integer Id;
    @ApiModelProperty("id")
    private String ids;
    @ApiModelProperty("名称")
    private String name;
}
