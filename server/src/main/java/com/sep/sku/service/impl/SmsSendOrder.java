package com.sep.sku.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sep.message.dto.SendLetterInput;
import com.sep.message.proxy.SmsAliyuncsProxy;
import com.sep.message.service.LetterTemplateService;
import com.sep.sku.enums.OrderStatus;
import com.sep.sku.model.Facilitator;
import com.sep.sku.model.OrderSku;
import com.sep.sku.model.SkuInfo;
import com.sep.sku.service.FacilitatorService;
import com.sep.sku.service.SkuInfoService;
import com.sep.user.input.GetUserByIdsInput;
import com.sep.user.output.UserOutput;
import com.sep.user.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SmsSendOrder {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private FacilitatorService facilitatorService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private SmsAliyuncsProxy smsAliyuncsProxy;


    @Autowired
    private LetterTemplateService letterTemplateService;


    private String merchants = "SMS_190728962";

    private String userTem = "SMS_192530457";


    public void orderSmsSend(List<OrderSku> orderSkuList) {

        if (!CollectionUtils.isEmpty(orderSkuList)) {
            List<Integer> skuIds = new ArrayList<>();
            List<Integer> userIds = new ArrayList<>();
            orderSkuList.forEach(e -> {
                skuIds.add(e.getSkuId());
                userIds.add(e.getUserId());
            });
            List<SkuInfo> skus = skuInfoService.getSkuByIds(skuIds);
            GetUserByIdsInput getUserByIdsInput = new GetUserByIdsInput();
            getUserByIdsInput.setUserIds(userIds);
            List<UserOutput> userOutputs = wxUserService.getUserByIds(getUserByIdsInput);
            log.info("orderSkuList  size: " + orderSkuList.size() + "-----{}", orderSkuList);
            orderSkuList.forEach(e -> {
                String userTel = null;
                Map<String, Object> message = new HashMap<>();
                for (UserOutput u : userOutputs) {
                    if (u.getId().equals(e.getUserId())) {
                        userTel = u.getTelnum();
                    }
                }
                message.put("consumable_code", e.getConsumableCode());
                for (SkuInfo s : skus) {
                    if (s.getId().equals(e.getSkuId())) {
                        message.put("sku_name", s.getSkuName());
                        message.put("period_time", s.getPeriodTime());
                        Facilitator facilitator = facilitatorService.getById(s.getFacilitatoId());
                        if (facilitator != null) {
                            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            message.put("period_time", df.format(s.getPeriodTime()));
                            message.put("facilitator_name", facilitator.getFacilitatorName());
                        }
                    }
                }
                if (userTel != null) {
                    log.info("发送短信用户: -----{}", userTel);
                    try {
                        smsAliyuncsProxy.sendSms(userTel, userTem, message);
                    } catch (ClientException e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    SendLetterInput sendLetterInput = new SendLetterInput();
                    sendLetterInput.setUserIds(Lists.newArrayList(e.getUserId()));
                    sendLetterInput.setTriggerPoint(1);
                    sendLetterInput.setMessages(message);
                    boolean messageResult = letterTemplateService.send(sendLetterInput);
                    if (messageResult != Boolean.TRUE) {
                        log.error("【messageLetterDeal】 发送消息失败,orderNo:{}", e.getOrderNo());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }


            });
            orderSkuList.forEach(e -> {
                String facilitatorTel = null;
                Map<String, Object> message = new HashMap<>();
                for (SkuInfo s : skus) {
                    if (s.getId().equals(e.getSkuId())) {
                        if (s.getFacilitatoId() != null && s.getFacilitatoId() > 0) {
                            Facilitator facilitator = facilitatorService.getById(s.getFacilitatoId());
                            facilitatorTel = facilitator.getPhoneNum();
                        }
                        message.put("sku_name", s.getSkuName());
                    }
                }
                message.put("consumable_code", e.getConsumableCode());
                for (UserOutput u : userOutputs) {
                    if (u.getId().equals(e.getUserId())) {
                        message.put("user_name", u.getNickname());
                    }
                }
                if (facilitatorTel != null) {
                    log.info("发送短信商户: -----{}", facilitatorTel);
                    try {
                        smsAliyuncsProxy.sendSms(facilitatorTel, merchants, message);
                    } catch (ClientException e1) {
                        e1.printStackTrace();
                    }
                }

            });



        }
    }
}
