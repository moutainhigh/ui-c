package com.sep.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "优惠卷首页查询请求对象", description = "优惠卷首页查询请求对象")
public class NominatesDto extends BaseDto {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "当前页")
    private Long currentPage = 0L;

    @ApiModelProperty(value = "每页长度")
    private Long pageSize = 10L;

}