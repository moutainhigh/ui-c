package com.sep.message.proxy;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class WxProxy {

    @Resource
    private RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${wx.message.send.url}")
    private String messageSendUrl;

    @Value("${token.prefix}")
    private String tokenPrefix;

    public WxMessageSendResp messageSend(String templateId, String touser, Object data) {
        WxMessageSend wxMessageSend = new WxMessageSend();
        wxMessageSend.setTouser(touser);
        wxMessageSend.setTemplate_id(templateId);
        wxMessageSend.setData(data);
        Map<String, Object> uriVariables = Maps.newHashMap();
        uriVariables.put("access_token", redisTemplate.opsForValue().get(tokenPrefix));
        return restTemplate.postForObject(messageSendUrl, wxMessageSend,
                WxMessageSendResp.class, uriVariables);
    }

}