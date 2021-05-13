package com.sep.distribution.dto;

import com.sep.common.utils.PageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

@Data
public class InvoiceUsersDto extends PageUtil {
    @NotNull(message = "订单id不能为空")
    @ApiModelProperty("订单id")
    private Integer orderID;
}
