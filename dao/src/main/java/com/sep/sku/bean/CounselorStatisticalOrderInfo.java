package com.sep.sku.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *  销售代表订单统计信息
 *
 * @author zhangkai
 * @date 2020年03月14日 22:08
 */
@Data
public class CounselorStatisticalOrderInfo {

    @ApiModelProperty(value = "销售人员id")
    private Integer counselorId;

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "商品总数量")
    private int totalSkuNum;
}