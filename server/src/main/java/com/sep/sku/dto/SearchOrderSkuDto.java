package com.sep.sku.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 分页查询订单商品
 *
 * @author zhangkai
 * @date 2019年12月28日 23:25
 */
@Data
public class SearchOrderSkuDto {

    @ApiModelProperty(value = "状态，0：待支付，1：已支付，2：已取消，3：已退款，4：已完成")
    private Integer status;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "当前页")
    private  Integer currentPage=0;

    @ApiModelProperty(value = "每页长度")
    private Integer pageSize=20;
}
