package com.sep.sku.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 按天统计订单量结果
 *
 * @author zhangkai
 * @date 2020年03月28日 22:17
 */
@Data
public class StatisticalMonthOrderCount {

    @ApiModelProperty(value = "日期")
    private String orderDay;

    @ApiModelProperty(value = "订单量")
    private int orderCount;
}
