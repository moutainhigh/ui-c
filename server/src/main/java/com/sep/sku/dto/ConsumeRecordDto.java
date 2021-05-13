package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 小程序端 消费记录参数
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="消费记录参数", description="消费记录参数")
public class ConsumeRecordDto {

    @ApiModelProperty(value = "起始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "支付方式，0：微信，1：积分")
    private Integer payWay;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "userId")
    private Integer userId;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize=20;

}
