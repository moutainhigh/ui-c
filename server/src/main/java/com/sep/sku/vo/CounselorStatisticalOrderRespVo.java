package com.sep.sku.vo;

import com.sep.sku.bean.CounselorStatisticalOrderInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 销售人员统计订单信息
 *
 * @author zhangkai
 * @date 2019年12月14日 22:52
 */
@Data
public class CounselorStatisticalOrderRespVo {

    @ApiModelProperty(value = "订单统计信息")
    private Map<Integer,CounselorStatisticalOrderInfo> statisticalOrderMap;


}
