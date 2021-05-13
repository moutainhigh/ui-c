package com.sep.sku.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 确认订单信息
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-22
 */
@Data
@ApiModel(value="提交订单返回信息", description="提交订单返回信息")
public class CommitOrderRespVo {

    @ApiModelProperty("支付方式，0：微信，1：积分")
    private int payWay;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("是否需要调起微信支付，积分支付和0元订单为false")
    private boolean isNeedWxPay = true;

    @ApiModelProperty("小程序ID")
    private String appId;

    @ApiModelProperty("时间戳")
    private String timeStamp;

    @ApiModelProperty("随机串，不长于32位")
    private String nonceStr;

    @ApiModelProperty("数据包，小程序端用package提交")
    private String wpackage;

    @ApiModelProperty("预支付id")
    private String prepayId;

    @ApiModelProperty("签名方式，默认为MD5")
    private String signType;

    @ApiModelProperty("签名串")
    private String paySign;
}
