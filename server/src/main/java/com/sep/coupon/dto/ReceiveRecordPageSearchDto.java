package com.sep.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "优惠卷领取记录查询请求对象", description = "优惠卷领取记录查询请求对象")
public class ReceiveRecordPageSearchDto {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "优惠券类型")
    private Integer type;

    @ApiModelProperty(value = "是否使用")
    private Integer useStatus;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiveStartDateTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiveEndDateTime;

    @ApiModelProperty(value = "当前页")
    private Long currentPage = 0L;

    @ApiModelProperty(value = "每页长度")
    private Long pageSize = 10L;

}