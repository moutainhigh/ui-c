package com.sep.sku.vo;

import com.sep.sku.model.Order;
import com.sep.user.output.UserAddressOutput;
import com.sep.user.vo.AddressVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 后台订单查询返回信息
 *
 * @author zhangkai
 * @date 2019年12月14日 22:52
 */
@Data
@ApiModel(value = "后台订单查询返回信息", description = "后台订单查询返回信息")
public class BackSearchOrderRespVo extends Order {

    @ApiModelProperty("物流信息")
    private List<SearchOrderLogisticsRespVo> orderLogisticsRespVoList;

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

    //    @ApiModelProperty(value = "商品集合")
//    private List<SearchOrderSkuRespVo> orderSkuList;
    @ApiModelProperty(value = "手机号码集合")
    private List<String> phones;
//    @ApiModelProperty(value = "收货地址信息")
//    private AddressVo addressVo;

    @ApiModelProperty(value = "订单状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "状态，0：待付款，1：待卖家发货，2：部分发货，3：待收货，4：已完成，5：交易关闭，6：已退款")
    private int statusCode;

}
