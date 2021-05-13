package com.sep.config;

import com.alibaba.fastjson.JSONObject;
import com.sep.common.enums.YesNo;
import com.sep.common.http.HttpClient4;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {


    @Value("${wx.appId}")
    private String appId;


    @Value("${wx.appSecret}")
    private String appSecret;


    @Value("${wx.getAccessToken}")
    private String getAccessToken;

    @Value("${token.prefix}")
    private String tokenPrefix;

    @Value("${token.isShowZdw}")
    private String isShowZdw;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @PostConstruct
    public void pingStart() {

        String redisIsShowZdw = redisTemplate.opsForValue().get(isShowZdw);
        log.info("启动服务获取的是否显示折到位按钮----------------->{}", redisIsShowZdw);
        if (StringUtils.isBlank(redisIsShowZdw)) {
            redisTemplate.opsForValue().set(isShowZdw, String.valueOf(YesNo.YES.getCode()));
        }

    }

    @Override
    public void run(String... args) throws Exception {
        log.info("启动服务获取的appId----------------->{}", appId);
        log.info("启动服务获取的SECRET----------------->{}", appSecret);

        while (true) {
            try {
                String url = getAccessToken.replaceAll("APPID", appId).
                        replaceAll("SECRET", appSecret);
                log.info("get AccessToken url ------>{}", url);
                String accessToken = HttpClient4.doGet(url);
                log.info("result token ------>{}", accessToken);
                JSONObject jsonObject = JSONObject.parseObject(accessToken);
                if (StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
                    redisTemplate.opsForValue().set(tokenPrefix, jsonObject.getString("access_token"));
                    log.info("启动服务后生成的access_token----------------->{}", jsonObject.getString("access_token"));
                    Thread.sleep((jsonObject.getInteger("expires_in") - 200) * 1000);// 休眠7000秒
                } else {
                    Thread.sleep(60 * 1000);
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (Exception e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }


    }
}
