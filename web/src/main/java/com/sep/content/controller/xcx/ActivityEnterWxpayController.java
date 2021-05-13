
package com.sep.content.controller.xcx;

import com.alibaba.fastjson.JSON;
import com.sep.common.controller.BaseController;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.utils.NumberArithmeticUtils;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.model.Activity;
import com.sep.content.model.ActivityEnterWxpay;
import com.sep.content.service.ActivityEnterWxpayService;
import com.sep.content.service.ActivityService;
import com.sep.sku.config.WxPayConfig;
import com.sep.content.dto.IdDto;
import com.sep.sku.dto.WxNotifyDealDto;
import com.sep.sku.enums.OrderStatus;
import com.sep.sku.model.Order;
import com.sep.sku.service.OrderService;
import com.sep.sku.tool.Paytool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * <p>
 * 用户付费项目表  前端控制器
 * </p>
 *
 * @author liutianao
 * @since 2020-09-16
 */
@RestController
@Slf4j
@RequestMapping("/xcx/activity/wx")
@Api(value = "活动-微信支付回调", tags = {"活动-微信支付回调相关API"})
public class ActivityEnterWxpayController {

    @Autowired
    private ActivityEnterWxpayService activityEnterWxpayService;

    @Autowired
    private WxPayConfig wxPayConfig;

    @Autowired
    private ActivityService activityService;

    @PostMapping(value = "/notify")
    @ApiOperation(value = "异步回调", httpMethod = "POST")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(">>>>>>>>>>>>>进入回调<<<<<<<<<<<<<<<<");

        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        log.info("【wx_notify】接收到的报文：{}", notityXml);

        Map map = Paytool.doXMLParse(notityXml);

        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {
            resXml = validateWxNotify(map);
        } else {
            resXml = wxReturnFailInfo("报文为空");
        }
        log.info("【wx_notify】微信回调，返回微信报文信息：{}", resXml);

        log.info(">>>>>>>>>>>>>微信支付回调数据结束<<<<<<<<<<<<<<<<");
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    private String validateWxNotify(Map map) {
        //验证签名是否正确
        Map<String, String> validParams = Paytool.paraFilter(map);  //回调验签时需要去除sign和空值参数
        String validStr = Paytool.createLinkString(validParams);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String sign = Paytool.sign(validStr, wxPayConfig.key, "utf-8").toUpperCase();//拼装生成服务器端验证的签名
        //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
        if (sign.equals(map.get("sign"))) {
            // 校验数据
            String orderNo = (String) map.get("out_trade_no");//订单号
            String amount = (String) map.get("total_fee");//价格
            String wxTradeNo = (String) map.get("transaction_id");//微信交易单号
            Integer totalPrice = Integer.valueOf(amount);//服务器这边记录的是钱的分

            ActivityEnterWxpay activityEnterWxpay = this.getByActivityNo(orderNo);
            Activity activity = this.getByActivityid(activityEnterWxpay.getActivityId());
            log.info("【wx_notify】活动信息：{}", JSON.toJSONString(activityEnterWxpay));

            if (activity == null) {
                log.warn("【wx_notify】活动不存在，order_no：{}", orderNo);
                // 没有订单，返回成功
                return wxReturnOkInfo();
            }
            if (activityEnterWxpay.getStatus() == OrderStatus.OVER.getCode()) {
                log.warn("【wx_notify】活动已处理，order_no：{}", orderNo);
                // 订单已处理，返回成功
                return wxReturnOkInfo();
            }
            if (NumberArithmeticUtils.safeMultiply(activity.getCost(), 100).intValue() != totalPrice) {
                log.error("【wx_notify】活动报名金额不正确，order_no：{},total_Price:{}", orderNo, totalPrice);
                // 订单金额不正确，返回失败
                return wxReturnFailInfo("活动报名金额不正确");
            }
            try {
                IdDto idDto = new IdDto();
                idDto.setId(activity.getId());
                activityService.subtractNum(idDto);
                activityEnterWxpayService.lambdaUpdate().eq(ActivityEnterWxpay::getActivityNo,orderNo)
                        .set(ActivityEnterWxpay::getWxTradeNo,wxTradeNo).set(ActivityEnterWxpay::getStatus,OrderStatus.OVER.getCode()).update();
            } catch (Exception e) {
                e.printStackTrace();
                return wxReturnFailInfo("活动报名处理失败");
            }
            return wxReturnOkInfo();
        } else {
            return wxReturnFailInfo("签名失败");
        }
    }

    private ActivityEnterWxpay getByActivityNo(String orderNo) {
        return activityEnterWxpayService.lambdaQuery().eq(ActivityEnterWxpay::getActivityNo, orderNo).one();
    }

    private Activity getByActivityid(Integer id) {

        return activityService.lambdaQuery().eq(Activity::getId, id).one();
    }

    private String wxReturnOkInfo() {
        String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        return resXml;
    }

    private String wxReturnFailInfo(String reason) {
        String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                + "<return_msg><![CDATA[" + reason + "]]></return_msg>" + "</xml> ";
        return resXml;
    }
}
