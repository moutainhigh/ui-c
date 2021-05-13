package com.sep.LiveBroadcast.controller.xcx;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.sep.LiveBroadcast.dto.GetLiveListDto;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "小程序直播调用接口", tags = {"小程序直播调用接口"})
@RequestMapping("/xcx/live")
public class XcxLiveBroadcastConroller {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${token.prefix}")
    private String tokenPrefix;

    @ApiModelProperty("获取直播列表")
    @PostMapping("/getList")
    public ResponseData get(@RequestBody GetLiveListDto getLiveListDto){
        String access_token = redisTemplate.opsForValue().get(tokenPrefix);
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("limit",getLiveListDto.getLimit());
        jsonObject1.put("start",getLiveListDto.getStart());
        String s = HttpUtil.doPostJson("https://api.weixin.qq.com/wxa/business/getliveinfo?access_token="+access_token, jsonObject1.toString());
        JSONObject jsonObject= JSONObject.parseObject(s);
        return ResponseData.OK(jsonObject);
    }

}
