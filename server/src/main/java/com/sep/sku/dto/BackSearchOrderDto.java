package com.sep.sku.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 后台订单查询 入参
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="后台分页查询商品订单信息", description="后台分页查询商品订单信息")
public class BackSearchOrderDto {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "下单开始时间")
    private String createTimeStart;

    @ApiModelProperty(value = "下单结束时间")
    private String createTimeEnd;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态，0：待支付，1：已支付，2：已取消，3：已退款，4：已完成")
    private Integer status;

    @ApiModelProperty(value = "支付类型，0：微信，1：积分")
    private Integer payWay;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private long pageSize=20;

}
