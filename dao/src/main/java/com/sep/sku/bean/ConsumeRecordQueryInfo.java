package com.sep.sku.bean;

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
public class ConsumeRecordQueryInfo {

    @ApiModelProperty(value = "起始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "支付方式，0：微信，1：积分")
    private Integer payWay;

    @ApiModelProperty(value = "userId")
    private Integer userId;

}
