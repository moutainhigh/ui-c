package com.sep.sku.vo;

import com.sep.sku.bean.StatisticalOrderInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 统计订单信息
 *
 * @author zhangkai
 * @date 2019年12月14日 22:52
 */
@Data
public class StatisticalOrderRespVo{

    @ApiModelProperty(value = "订单统计信息")
    private Map<Integer,StatisticalOrderInfo> statisticalOrderMap;


}
