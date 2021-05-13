package com.sep.coupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@ApiModel(value = "优惠卷分页查询请求对象", description = "优惠卷分页查询请求对象")
public class CouponPageSearchDto {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "优惠券类型")
    private Integer type;

    @ApiModelProperty(value = "发行状态")
    private Integer publishStatus;

    @ApiModelProperty(value = "是否过期:0 未过期,1 已过期")
    private Integer expiresStatus;

    @ApiModelProperty(value = "是否领完:0 未领完,1 已领完")
    private Integer receiveStatus;

    @ApiModelProperty(value = "开始时间")
    private LocalDate startDateTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDate endDateTime;

    @ApiModelProperty(value = "当前页")
    private Long currentPage = 0L;

    @ApiModelProperty(value = "每页长度")
    private Long pageSize = 10L;

}