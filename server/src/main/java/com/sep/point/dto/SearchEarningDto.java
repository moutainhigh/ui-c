package com.sep.point.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.oval.constraint.NotNull;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "收益查询请求对象", description = "收益查询请求对象")
public class SearchEarningDto extends BaseDto {

    private Integer userId;

    @ApiModelProperty(value = "开始时间")
    @NotNull(message = "开始时间不能为空")
    private LocalDate startTime;

    @ApiModelProperty(value = "结束时间")
    @NotNull(message = "结束时间不能为空")
    private LocalDate endTime;

}