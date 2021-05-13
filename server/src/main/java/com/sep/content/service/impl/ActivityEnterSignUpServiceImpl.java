package com.sep.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.common.utils.NumberArithmeticUtils;
import com.sep.content.dto.*;
import com.sep.content.enums.BizErrorCode;
import com.sep.content.model.*;
import com.sep.content.repository.ActivityEnterMapper;
import com.sep.content.repository.ActivityEnterSignUpMapper;
import com.sep.content.service.*;
import com.sep.content.vo.ActivityEnterSignUpVo;
import com.sep.content.vo.ActivityEnterSignUpXcxVo;
import com.sep.content.vo.ActivityEnterVo;
import com.sep.content.vo.EnterRespVo;
import com.sep.sku.config.WxPayConfig;
import com.sep.sku.tool.Paytool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivityEnterSignUpServiceImpl extends ServiceImpl<ActivityEnterSignUpMapper, ActivityEnterSignUp> implements ActivityEnterSignUpService {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private IncreaseIntegral increaseIntegral;
    @Autowired
    private ActivitySignUpService activitySignUpService;
    @Autowired
    private WxPayConfig wxPayConfig;
    @Autowired
    private ActivityEnterWxpayService activityEnterWxpayService;

    @Override
    public EnterRespVo enter(AddEnterDto addEnterDto) {
        String userId = JwtUtils.parseJWT(addEnterDto.getToken()).get("id").toString();
        if (addEnterDto.getActivityId() == 0 || addEnterDto.getActivityId() == null) {
            return null;
        }
        Integer count = lambdaQuery().eq(ActivityEnterSignUp::getActivityId, addEnterDto.getActivityId())
                .eq(ActivityEnterSignUp::getUserId, userId).count();
        if (count > 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "您已进行过报名");
        }
        if (StringUtils.isNotBlank(userId)) {
            List<ActivityEnterSignUpDto> activityEnterSignUpDtos = addEnterDto.getActivityEnterSignUpDtos();
            List<Boolean> collect;
            if (activityEnterSignUpDtos != null && activityEnterSignUpDtos.size() != 0) {
                collect = activityEnterSignUpDtos.stream().map(e -> {
                    ActivityEnterSignUp enter = new ActivityEnterSignUp();
                    enter.setIsApply(addEnterDto.getIsApply());
                    enter.setActivityId(addEnterDto.getActivityId());
                    enter.setInformation(e.getInformation());
                    enter.setSignId(e.getId());
                    enter.setUserId(Integer.parseInt(userId));
                    enter.setCreatedTime(LocalDateTime.now());
                    boolean insert = enter.insert();
                    return insert;
                }).collect(Collectors.toList());
                String newActivityNo = getNewActivityNo();
                newActivityNo="A"+newActivityNo;
                ActivityEnterWxpayDto activityEnterWxpayDto = new ActivityEnterWxpayDto();
                activityEnterWxpayDto.setActivityId(addEnterDto.getActivityId());
                activityEnterWxpayDto.setStatus(0);
                activityEnterWxpayDto.setActivityNo(newActivityNo);
                activityEnterWxpayDto.setAmount(addEnterDto.getCost());
                activityEnterWxpayDto.setUserId(Integer.parseInt(userId));
                activityEnterWxpayService.add(activityEnterWxpayDto);
                EnterRespVo enterRespVo = new EnterRespVo();
                String openId = JwtUtils.parseJWT(addEnterDto.getToken()).get("openid").toString();
                IdDto idDto = new IdDto();
                idDto.setId(addEnterDto.getActivityId());

                if (addEnterDto.getCost() != null && !addEnterDto.getCost().equals(BigDecimal.ZERO)) {
                    wxPayRequest(addEnterDto.getIp(), openId, newActivityNo, addEnterDto.getCost(), enterRespVo);
                } else {
                    enterRespVo.setNeedWxPay(false);
                }
                Integer currentNum = activityService.getCurrentNum(addEnterDto.getActivityId());
                if (currentNum == 0) {
                    throw new SepCustomException(BizErrorCode.ACTIVITYENTER_ERROR);
                }
                enterRespVo.setActivityId(addEnterDto.getActivityId());
                return enterRespVo;
            }

        }

        return null;
    }

    private String getNewActivityNo() {
        String random = RandomStringUtils.random(10, false, true);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//获取年
        String orderNo = year + "" + random;
        Integer activityEnterCount = activityEnterWxpayService.pageSearchInfo(orderNo);
        if (activityEnterCount != null && activityEnterCount > 0) {
            return getNewActivityNo();
        }
        return orderNo;
    }

    private void wxPayRequest(String ip, String openId, String orderNo, BigDecimal totalAmount, EnterRespVo enterRespVo) {
        try {
            //生成的随机字符串
            String nonce_str = Paytool.getRandomStringByLength(32);
            //商品名称
            String body = orderNo;
            BigDecimal price = NumberArithmeticUtils.safeMultiply(totalAmount, 100);
            String totalFree = String.valueOf(price.intValue());
            //组装参数，用户生成统一下单接口的签名
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", wxPayConfig.appid);
            packageParams.put("mch_id", wxPayConfig.mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", orderNo);//商户订单号
            packageParams.put("total_fee", totalFree);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", ip);
            packageParams.put("notify_url", wxPayConfig.notify_url);//支付成功后的回调地址
            packageParams.put("trade_type", wxPayConfig.TRADETYPE);//支付方式
            packageParams.put("openid", openId);

            String prestr = Paytool.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String sign = Paytool.sign(prestr, wxPayConfig.key, "utf-8").toUpperCase();

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + wxPayConfig.appid + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + wxPayConfig.mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + wxPayConfig.notify_url + "</notify_url>"
                    + "<openid>" + openId + "</openid>"
                    + "<out_trade_no>" + orderNo + "</out_trade_no>"
                    + "<spbill_create_ip>" + ip + "</spbill_create_ip>"
                    + "<total_fee>" + totalFree + "</total_fee>"
                    + "<trade_type>" + wxPayConfig.TRADETYPE + "</trade_type>"
                    + "<sign>" + sign + "</sign>"
                    + "</xml>";

            log.info("调试模式_统一下单接口 请求XML数据：{}", xml);

            //调用统一下单接口，并接受返回的结果
            String result = Paytool.httpRequest(wxPayConfig.pay_url, "POST", xml);

            log.info("调试模式_统一下单接口 返回XML数据：{}" + result);

            // 将解析结果存储在HashMap中
            Map map = Paytool.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码

            if (return_code.equals("SUCCESS")) {
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                enterRespVo.setPrepayId(prepay_id);
                enterRespVo.setNonceStr(nonce_str);
                enterRespVo.setWpackage("prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                enterRespVo.setTimeStamp(timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                //拼接签名需要的参数
                String stringSignTemp = "appId=" + wxPayConfig.appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = Paytool.sign(stringSignTemp, wxPayConfig.key, "utf-8").toUpperCase();

                enterRespVo.setPaySign(paySign);
            }
            enterRespVo.setAppId(wxPayConfig.appid);
            log.info("【commitOrder】->commitOrderRespVo : {}", JSON.toJSONString(enterRespVo));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "调取微信支付失败");
        }
    }

    @Override
    public ActivityEnterSignUpVo searchActivityEnter(SearchEnterSignUpDto searchEnterDto) {
        Activity activity = activityService.lambdaQuery().eq(Activity::getId, searchEnterDto.getActiveId()).one();
        List<ActivityEnterSignUp> list =null;
        if(activity.getCost()==null||activity.getCost().equals(BigDecimal.ZERO)){
            list  = lambdaQuery()
                    .eq(searchEnterDto.getUserId() != null && searchEnterDto.getUserId() != 0, ActivityEnterSignUp::getUserId, searchEnterDto.getUserId())
                    .eq(searchEnterDto.getIsApply() != null && searchEnterDto.getIsApply() != 0, ActivityEnterSignUp::getIsApply, searchEnterDto.getIsApply())
                    .eq(ActivityEnterSignUp::getActivityId, searchEnterDto.getActiveId()).list();
        }else {
            list  = lambdaQuery()
                    .eq(searchEnterDto.getUserId() != null && searchEnterDto.getUserId() != 0, ActivityEnterSignUp::getUserId, searchEnterDto.getUserId())
                    .eq(searchEnterDto.getIsApply() != null && searchEnterDto.getIsApply() != 0, ActivityEnterSignUp::getIsApply, searchEnterDto.getIsApply())
                    .in(ActivityEnterSignUp::getUserId,activityEnterWxpayService.lambdaQuery().eq(ActivityEnterWxpay::getActivityId,searchEnterDto.getActiveId()).list().stream().map(ActivityEnterWxpay::getUserId).distinct().collect(Collectors.toList()))
                    .eq(ActivityEnterSignUp::getActivityId, searchEnterDto.getActiveId()).list();
        }
        if (list != null && list.size() != 0 && activity != null) {
            ActivityEnterSignUpVo activityEnterSignUpVo = new ActivityEnterSignUpVo();
            activityEnterSignUpVo.setActivityName(activity.getTitle());
            activityEnterSignUpVo.setActivityId(activity.getId());
            Map<Integer, List<ActivityEnterSignUp>> map = new HashMap<>();
            List<Integer> collect = list.stream().map(e -> {
                if (map.get(e.getUserId()) == null) {
                    List<ActivityEnterSignUp> list1 = new ArrayList<>();
                    list1.add(e);
                    map.put(e.getUserId(), list1);
                } else {
                    List<ActivityEnterSignUp> activityEnterSignUps = map.get(e.getUserId());
                    activityEnterSignUps.add(e);
                    map.put(e.getUserId(), activityEnterSignUps);
                }
                return e.getUserId();
            }).collect(Collectors.toList());
            List<Integer> collect1 = collect.stream().distinct().collect(Collectors.toList());
            List<Map<Integer, String>> lists = new ArrayList<>();
            collect1.forEach(e -> {
                HashMap<Integer, String> map1 = new HashMap();
                map.get(e).forEach(a -> {
                    map1.put(-1, "" + a.getUserId());
                    map1.put(-2, getTime(a.getCreatedTime()));
                    map1.put(a.getSignId(), a.getInformation());
                });
                lists.add(map1);
            });
            activityEnterSignUpVo.setList(lists);
            return activityEnterSignUpVo;
        }
        return null;
    }

    @Override
    public ActivityEnterSignUpXcxVo searchActivityXcxEnter(SearchEnterSignUpDto searchEnterSignUpDto) {
        Activity activity = activityService.lambdaQuery().eq(Activity::getId, searchEnterSignUpDto.getActiveId()).one();
        List<ActivityEnterSignUp> list =null;
        if(activity.getCost()==null||activity.getCost().equals(BigDecimal.ZERO)){
            list  = lambdaQuery()
                    .eq(searchEnterSignUpDto.getUserId() != null && searchEnterSignUpDto.getUserId() != 0, ActivityEnterSignUp::getUserId, searchEnterSignUpDto.getUserId())
                    .eq(searchEnterSignUpDto.getIsApply() != null && searchEnterSignUpDto.getIsApply() != 0, ActivityEnterSignUp::getIsApply, searchEnterSignUpDto.getIsApply())
                    .eq(ActivityEnterSignUp::getActivityId, searchEnterSignUpDto.getActiveId()).list();
        }else {
            List<Integer> collect = activityEnterWxpayService.lambdaQuery().eq(ActivityEnterWxpay::getActivityId, searchEnterSignUpDto.getActiveId()).list().stream().map(ActivityEnterWxpay::getUserId).distinct().collect(Collectors.toList());

            list  = lambdaQuery()
                    .eq(searchEnterSignUpDto.getUserId() != null && searchEnterSignUpDto.getUserId() != 0, ActivityEnterSignUp::getUserId, searchEnterSignUpDto.getUserId())
                    .eq(searchEnterSignUpDto.getIsApply() != null && searchEnterSignUpDto.getIsApply() != 0, ActivityEnterSignUp::getIsApply, searchEnterSignUpDto.getIsApply())
                    .in(collect.size()!=0,ActivityEnterSignUp::getUserId,collect)
                    .eq(ActivityEnterSignUp::getActivityId, searchEnterSignUpDto.getActiveId()).list();
        }
        if (list != null && list.size() != 0 && activity != null) {
            ActivityEnterSignUpXcxVo activityEnterSignUpVo = new ActivityEnterSignUpXcxVo();
            activityEnterSignUpVo.setActivityName(activity.getTitle());
            activityEnterSignUpVo.setActivityId(activity.getId());
            Map<Integer, List<ActivityEnterSignUp>> map = new HashMap<>();
            List<Integer> collect = list.stream().map(e -> {
                if (map.get(e.getUserId()) == null) {
                    List<ActivityEnterSignUp> list1 = new ArrayList<>();
                    list1.add(e);
                    map.put(e.getUserId(), list1);
                } else {
                    List<ActivityEnterSignUp> activityEnterSignUps = map.get(e.getUserId());
                    activityEnterSignUps.add(e);
                    map.put(e.getUserId(), activityEnterSignUps);
                }
                return e.getUserId();
            }).collect(Collectors.toList());
            List<Integer> collect1 = collect.stream().distinct().collect(Collectors.toList());
            List<Map<String, String>> lists = new ArrayList<>();
            collect1.forEach(e -> {
                map.get(e).forEach(a -> {
                    HashMap<String, String> map1 = new HashMap();
                    ActivitySignUp one = activitySignUpService.lambdaQuery().eq(ActivitySignUp::getId, a.getSignId()).one();
                    map1.put("name", a.getInformation());
                    map1.put("value", a.getInformation());
                    lists.add(map1);
                });
            });
            activityEnterSignUpVo.setList(lists);
            return activityEnterSignUpVo;
        }
        return null;
    }

    private String getTime(LocalDateTime localDateTime) {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dtf2.format(localDateTime);
    }
}
