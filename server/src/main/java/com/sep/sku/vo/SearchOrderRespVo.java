package com.sep.sku.vo;

import com.sep.sku.model.Order;
import com.sep.sku.model.OrderInformation;
import com.sep.user.output.UserAddressOutput;
import com.sep.user.vo.AddressVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * TODO
 *
 * @author zhangkai
 * @date 2019年12月14日 22:52
 */
@Data
public class SearchOrderRespVo extends Order {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "支付类型名称")
    private String payWayName;

    @ApiModelProperty(value = "订单状态名称")
    private String statusName;

    @ApiModelProperty(value = "购买商品总数量")
    private int skuTotalCount;
//        @ApiModelProperty(value = "商品集合")
//    private List<SearchOrderSkuRespVo> orderSkuList;
    @ApiModelProperty(value = "商品名称")
    private String skuName;
    @ApiModelProperty(value = "消费码")
    private String consumableCode;
    @ApiModelProperty(value = "商品首张图片")
    private String skuFirstPictureUrl;

    @ApiModelProperty(value = "消费总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "原价")
    private BigDecimal originPrice;
    @ApiModelProperty(value = "手机号码集合")
    private List<String> phones;
    @ApiModelProperty(value = "收货地址信息")
    private AddressVo addressVo;

    @ApiModelProperty(value = "状态，0：待支付，1：已完成，2：已取消，3：已退款")
    private int statusCode;
    @ApiModelProperty(value = "状态，0:未开发票，1：已开发票")
    private int statusInvoice;

    @ApiModelProperty(value = "订单状态描述")
    private String statusDesc;

}
