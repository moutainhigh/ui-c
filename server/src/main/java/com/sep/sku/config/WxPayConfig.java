package com.sep.sku.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 小程序支付相关配置
 */
@Component
public class WxPayConfig {

    // 小程序appid
    @Value("${wx.appId}")
    public String appid;
    // 微信支付的商户id
    @Value("${wx.mch_id}")
    public String mch_id;
    // 微信支付的商户密钥
    @Value("${wx.key}")
    public String key;
    // 支付成功后的服务器回调url
    @Value("${wx.notify_url}")
    public String notify_url;
    //签名方式，固定值
    public String SIGNTYPE = "MD5";
    //交易类型，小程序支付的固定值为JSAPI
    public String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

}