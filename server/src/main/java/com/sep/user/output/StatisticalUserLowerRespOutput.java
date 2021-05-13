package com.sep.user.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@ApiModel(value = "用户下级统计返回对象", description = "用户下级统计返回对象")
@Data
public class StatisticalUserLowerRespOutput {

    @ApiModelProperty("订单统计信息")
    private Map<Integer, StatisticalUserLowerOutput> statisticalMap;

}