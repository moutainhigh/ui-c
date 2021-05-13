package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.oval.constraint.NotNull;

/**
 * <p>
 * 首页订单统计 入参
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel("首页统计参数信息")
public class HomeOrderStatisticalDto {

    @ApiModelProperty(value = "统计月份，格式 2020-03")
    @NotNull
    private String date;

}
