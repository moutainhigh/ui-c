package com.sep.sku.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 消费记录返回参数
 *
 * @author zhangkai
 * @date 2019年12月14日 22:52
 */
@Data
public class ConsumeRecordRespVo{

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "总积分")
    private int totalIntegral;

    @ApiModelProperty(value = "订单分页信息")
    IPage<SearchOrderRespVo> orderPageInfo;

}
