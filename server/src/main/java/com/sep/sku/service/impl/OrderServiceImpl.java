package com.sep.sku.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.sep.common.http.HttpClient4;
import com.sep.coupon.dto.UseCouponInput;
import com.sep.coupon.service.CouponService;
import com.sep.distribution.dto.CashbackDto;
import com.sep.distribution.model.InvoiceUser;
import com.sep.distribution.service.CashBackService;
import com.sep.distribution.service.InvoiceUserService;
import com.sep.point.dto.PointIncreaseInput;
import com.sep.point.dto.ProductsExchangeInput;
import com.sep.message.service.LetterTemplateService;
import com.sep.point.service.PointService;
import com.sep.sku.bean.*;
import com.sep.sku.bean.CounselorStatisticalOrderInfo;
import com.sep.sku.bean.StatisticalOrderInfo;
import com.sep.sku.dto.CounselorStatisticalOrderDto;
import com.sep.sku.dto.ServerBatchSearchOrderDto;
import com.sep.sku.dto.StatisticalOrderDto;
import com.sep.sku.enums.*;
import com.sep.sku.vo.*;
import com.sep.sku.config.WxPayConfig;
import com.sep.sku.dto.*;
import com.sep.sku.enums.PayWayEnum;
import com.sep.sku.model.*;
import com.sep.sku.repository.OrderMapper;
import com.sep.sku.service.*;
import com.sep.sku.tool.Paytool;
import com.sep.user.input.GetUserAddressInput;
import com.sep.user.output.UserAddressOutput;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.common.utils.LocalDateTimeUtils;
import com.sep.common.utils.NumberArithmeticUtils;
import com.sep.message.dto.SendLetterInput;
import com.sep.user.service.AddressService;
import com.sep.user.vo.AddressVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

/**
 * <p>
 * 商品订单 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderSkuService orderSkuService;
    @Autowired
    private OrderInformationService orderInformation;
    @Autowired
    private OrderPayLogService orderPayLogService;

    @Autowired
    private CashBackService cashBackService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuPropertyService orderSkuPropertyService;

    @Autowired
    private OrderLogisticsService orderLogisticsService;

    @Autowired
    private PointService pointService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CartService cartService;

    @Autowired
    private LetterTemplateService letterTemplateService;

    @Autowired
    private WxPayConfig wxPayConfig;
    @Autowired
    private InvoiceUserService invoiceUserService;

    @Autowired
    private SmsSendOrder smsSendOrder;

    @Autowired
    private AddressService addressService;
    public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
    /**
     * 手机号码正则表达式=^(13[0-9]|15[0|3|6|7|8|9]|18[0,5-9])\d{8}$
     */
    public static final String MOBILE = "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\\\d{8}$\";";
    @Value("${sku_type.rebate}")
    private Integer rebate;


    @Value("${sku_type.volume}")
    private Integer volume;

    @Value("${zdw_notify_url}")
    private String zdwNotifyUrl;

    @Value("${zdw_get_skuid}")
    private String zdwGetSkuId;
    @Autowired
    private OrderInformationService orderInformationService;

    @Override
    @Transactional
    public CommitOrderRespVo commitOrder(CommitOrderDto commitOrderDto) {
        log.info("【commitOrder】->commitOrderDto : {}", JSON.toJSONString(commitOrderDto));

        if (PayWayEnum.valueOf(commitOrderDto.getPayWay()) == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "支付方式未知");
        }
        if (Objects.isNull(commitOrderDto.getSkuId())) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "购买商品不能为空");
        }
        boolean currentOrderPaywayIsWx = commitOrderDto.getPayWay() == PayWayEnum.WEIXIN_PAY.getCode();
//        if (!currentOrderPaywayIsWx && commitOrderDto.getCouponId() != null) {
//            throw new SepCustomException(ResponseData.STATUS_CODE_400, "优惠券和积分不能同时使用");
//        }
        // 提交订单返回信息
        CommitOrderRespVo commitOrderRespVo = new CommitOrderRespVo();
        commitOrderRespVo.setPayWay(commitOrderDto.getPayWay());

        int userId = (int) JwtUtils.parseJWT(commitOrderDto.getToken()).get("id");
        String unionId = "";
        String openId = JwtUtils.parseJWT(commitOrderDto.getToken()).get("openid").toString();
        log.info("【commitOrder】->userId : {}", userId);
        log.info("【commitOrder】->openId : {}", openId);

        // 是否支持积分兑换
        boolean isIntegralExchange = true;
        // 是否支持现金支付
        boolean isCashPay = true;
        // 兑换所需总积分
        int totalIntegralNum = 0;
        // 支付所需总金额
        BigDecimal originTotalAmount = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        Map<String, SearchSkuRespVo> skuRespVoMap = Maps.newHashMap();
        Map<String, Integer> skuByNumMap = Maps.newHashMap();
        Map<String, List<SettlementPropertyInfo>> skuPropertyInfoMap = Maps.newHashMap();
        Map<String, CouponValuationRespSkuInfo> couponValucationSkuInfoMap = Maps.newHashMap();
        List<String> skuUniquKeyList = Lists.newArrayList();
        Map<Integer, Integer> skuBuyNumMap = Maps.newHashMap();
        List<String> phones = new ArrayList<>();
//        for (CommitOrderSkuInfo commitOrderSkuInfo : commitOrderDto.getBuySkuList()) {
        if (commitOrderDto.getPhones() == null || commitOrderDto.getPhones().size() != commitOrderDto.getBuyNum()) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "请填写全部手机号码");
        }
        commitOrderDto.getPhones().forEach(e -> {
            if (getPhone(e) == 0) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "请填写正确的手机号码");
            }
        });
        phones.addAll(commitOrderDto.getPhones());
        SearchSkuRespVo searchSkuRespVo = null;
        try {
            searchSkuRespVo = settlementService.validateSkuForBuy(commitOrderDto.getSkuId(), commitOrderDto.getBuyNum());
//                searchSkuRespVo.setEncryptParam(commitOrderDto.getEncryptParam());
        } catch (SepCustomException sepException) {
            throw sepException;
        }
        String skuUniquKey = commitOrderDto.getSkuUniqueKey();
        skuUniquKeyList.add(skuUniquKey);
        skuByNumMap.put(skuUniquKey, commitOrderDto.getBuyNum());
//            skuRespVoMap.put(skuUniquKey, searchSkuRespVo);
//            totalIntegralNum = totalIntegralNum + (searchSkuRespVo.getIntegralNum() * commitOrderSkuInfo.getBuyNum());
//            isIntegralExchange = isIntegralExchange && searchSkuRespVo.getIsIntegralExchange();
//            isCashPay = isCashPay && searchSkuRespVo.getIsCashPay();
//            if (!StringUtils.isEmpty(commitOrderSkuInfo.getEncryptParam())) {
//                ZdwCouponDto zdwCouponDto = new ZdwCouponDto();
//                try {
//                    zdwCouponDto = settlementService.getZdwCouponDto(commitOrderSkuInfo.getEncryptParam());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (zdwCouponDto != null && zdwCouponDto.getFacilitatoSkuId().equals(commitOrderSkuInfo.getSkuId())) {
//                    if (commitOrderSkuInfo.getBuyNum() > 1) {
//                        BigDecimal currentSkuTotalAmount = NumberArithmeticUtils.safeMultiply(searchSkuRespVo.getCurrentPrice(), commitOrderSkuInfo.getBuyNum() - 1);
//                        totalAmount = NumberArithmeticUtils.safeAdd(settlementService.getSkuPrice(zdwCouponDto, searchSkuRespVo.getCurrentPrice()), currentSkuTotalAmount);
//
//                    } else {
//                        totalAmount = settlementService.getSkuPrice(zdwCouponDto, searchSkuRespVo.getCurrentPrice());
//                    }
//                    searchSkuRespVo.setCurrentPrice(totalAmount);
//                }
//            } else {
        BigDecimal currentSkuAmount = NumberArithmeticUtils.safeMultiply(searchSkuRespVo.getCurrentPrice(), commitOrderDto.getBuyNum());
        totalAmount = NumberArithmeticUtils.safeAdd(totalAmount, currentSkuAmount);
        originTotalAmount = totalAmount;
//            }
//            if (!CollectionUtils.isEmpty(commitOrderSkuInfo.getSettlementPropertyInfoList())) {
//                skuPropertyInfoMap.put(skuUniquKey, commitOrderSkuInfo.getSettlementPropertyInfoList());
//            }
        Integer skuBuyNum = skuBuyNumMap.get(commitOrderDto.getSkuId());
        if (skuBuyNum == null) {
            skuBuyNum = commitOrderDto.getBuyNum();
        } else {
            skuBuyNum = skuBuyNum + commitOrderDto.getBuyNum();
        }
        skuBuyNumMap.put(commitOrderDto.getSkuId(), skuBuyNum);
//        }

//        if (currentOrderPaywayIsWx) {
//            if (!isCashPay) {
//                throw new SepCustomException(ResponseData.STATUS_CODE_400, "此订单不支持现金支付");
//            }
//        } else {
//            if (!isIntegralExchange) {
//                throw new SepCustomException(ResponseData.STATUS_CODE_400, "此订单不支持积分兑换");
//            }
//        }

//        if (commitOrderDto.getCouponId() != null) {
//            // 优惠券计价
//            CouponValuationDto couponValuationDto = new CouponValuationDto();
//            couponValuationDto.setCouponId(commitOrderDto.getCouponId());
//            List<CouponValuationSkuInfo> couponValuationSkuInfos = commitOrderDto.getBuySkuList().stream().map(buySku -> {
//                CouponValuationSkuInfo couponValuationSkuInfo = new CouponValuationSkuInfo();
//                BeanUtils.copyProperties(buySku, couponValuationSkuInfo);
//                return couponValuationSkuInfo;
//            }).collect(Collectors.toList());
//            couponValuationDto.setBuySkuList(couponValuationSkuInfos);
//            CouponValuationRespVo couponValuationRespVo = settlementService.couponValuation(couponValuationDto);
//            List<CouponValuationRespSkuInfo> couponValuationRespSkuInfoList = couponValuationRespVo.getBuySkuList();
//            couponValucationSkuInfoMap = couponValuationRespSkuInfoList.stream().collect(Collectors.toMap(
//                    CouponValuationRespSkuInfo::getSkuUniqueKey, couponValuationRespSkuInfo -> couponValuationRespSkuInfo));
//            totalAmount = couponValuationRespVo.getTotalAmount();
//        }

        if (!currentOrderPaywayIsWx) {
            // todo 查询积分是否足够，考虑并发下单情况
            int userRemainIntegral;
            try {
                userRemainIntegral = pointService.current(userId);
                if (userRemainIntegral < totalIntegralNum) {
                    throw new SepCustomException(ResponseData.STATUS_CODE_400, "用户积分不足");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "查询用户积分失败");
            }
        }
        // 生成订单号
        String orderNo = getNewOrderNo();
        // 保存订单
        Order order = saveOrderAndSku(orderNo,commitOrderDto.getRemarks(), commitOrderDto, skuByNumMap, userId, unionId, skuRespVoMap, skuPropertyInfoMap, originTotalAmount, totalAmount, totalIntegralNum, couponValucationSkuInfoMap);
        orderInformationService.add(phones, orderNo, commitOrderDto.getSkuId());
        log.info("【commitOrder】->orderInfo : {}", JSON.toJSONString(order));

        if (!currentOrderPaywayIsWx) {
            // 调用积分服务扣减积分
            pointConsumeDeal(order.getId(), orderNo, totalIntegralNum, userId);
            // 修改商品销售数量
            skuInfoService.skuSaleNumDeal(skuBuyNumMap);
        } else {
            if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
                // 请求微信接口获取小程序支付相关信息
                wxPayRequest(commitOrderDto.getIp(), openId, orderNo, totalAmount, commitOrderRespVo);
                if (StringUtils.isEmpty(commitOrderRespVo.getPrepayId())) {
                    throw new SepCustomException(ResponseData.STATUS_CODE_400, "生成预支付id失败");
                }
                // 保存订单交易日志表
                saveOrderPayLog(userId, openId, orderNo, commitOrderRespVo.getPrepayId());
            }
        }
//
//        if (commitOrderDto.getCouponId() != null) {
//            // 处理优惠券状态
//            useCouponDeal(commitOrderDto.getCouponId(), userId, originTotalAmount, totalAmount, order.getId(), orderNo);
//        }
        //  来源是购物车的商品从购物车中删除
        if (commitOrderDto.getSource() == 1) {
            cartDeal(skuUniquKeyList, commitOrderDto.getToken());
        }
        // 发送订单相关消息
        messageLetterDeal(userId, order);

        commitOrderRespVo.setOrderNo(orderNo);
        commitOrderRespVo.setNeedWxPay(currentOrderPaywayIsWx && totalAmount.compareTo(BigDecimal.ZERO) > 0);
        return commitOrderRespVo;
    }

    private Integer getPhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE);
        Pattern pattern1 = Pattern.compile(MOBILE);
        Matcher matcher = pattern.matcher(phone);
        boolean matches = matcher.matches();
        Matcher matcher1 = pattern1.matcher(phone);
        boolean matches1 = matcher1.matches();
        if (!matches && !matches1) {
            return 0;
        }
        return 1;
    }

    private String getRandomString(int length) {
        //产生随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //循环length次
        for (int i = 0; i < length; i++) {
            //产生0-2个随机数，既与a-z，A-Z，0-9三种可能
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                //如果number产生的是数字0；
                case 0:
                    //产生A-Z的ASCII码
                    result = Math.round(Math.random() * 25 + 65);
                    //将ASCII码转换成字符
                    sb.append((char) result);
                    break;
                case 1:
                    //产生a-z的ASCII码
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append((char) result);
                    break;
                case 2:
                    //产生0-9的数字
                    sb.append(new Random().nextInt(10));
                    break;
            }
        }
        return sb.toString();
    }

    @Override
    @Transactional
    public void wxNotifyDeal(WxNotifyDealDto wxNotifyDealDto) {
        String orderNo = wxNotifyDealDto.getOrderNo();
        LocalDateTime payTime = now();
        if (StringUtils.isEmpty(orderNo)) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单号不能为空");
        }
        Order order = getSkuOrderInfoByOrderNo(orderNo);
        if (order == null) {
            log.warn("【wxNotifyDeal】 订单不存在,orderNo:{}", orderNo);
            return;
        }
        order.setStatus(OrderStatus.OVER.getCode());
        order.setPayTime(payTime);
        order.setWxTradeNo(wxNotifyDealDto.getWxTradeNo());
        int orderRes = baseMapper.updateById(order);
        if (orderRes <= 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单状态更新失败");
        }
//        List<OrderSku> orderSkuList = orderSkuService.findSkuListByOrderNo(orderNo);
//        if (CollectionUtils.isEmpty(orderSkuList)) {
//            log.warn("【wxNotifyDeal】 订单商品不存在,orderNo:{}", orderNo);
//            return;
//        }
////        orderSkuList.forEach(e -> {
////            e.setStatus(OrderStatus.OVER.getCode());
////            e.setConsumableCode(this.getRandomString(8));
//////            if (!Objects.isNull(e.getZdwOrderSkuId()) && e.getZdwOrderSkuId() > 0) {
//////                HttpClient4.doGet(zdwNotifyUrl + e.getZdwOrderSkuId());
//////            }
////        });
//        boolean orderSkuRes = orderSkuService.updateBatchById(orderSkuList);
//        if (!orderSkuRes) {
//            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单商品状态更新失败");
//        }

        // 更新该订单对应的最后一个交易记录为成功，更新失败不影响主流程
        List<OrderPayLog> payLogList = orderPayLogService.getPayLogListByOrderNo(orderNo);
        if (CollectionUtils.isEmpty(payLogList)) {
            log.warn("【wxNotifyDeal】 交易日志不存在,orderNo:{}", orderNo);
            return;
        }
        OrderPayLog orderPayLog = payLogList.get(0);
        orderPayLog.setWxTradeNo(wxNotifyDealDto.getWxTradeNo());
        orderPayLog.setPayTime(payTime);
        boolean payLogResult = orderPayLogService.updateById(orderPayLog);
        if (!payLogResult) {
            log.warn("【wxNotifyDeal】 更新交易日志状态失败,orderNo:{}", orderNo);
        }
        // 分销处理
        cashBackDeal(order, payTime);
        // 发送订单支付通知
        log.info("开始发送消息........................................");
        messageLetterDeal(order.getUserId(), order);

        //发短信通知用户和商户
        // smsSendOrder.orderSmsSend(orderSkuList);
        //  增加积分
        //  increasePointDeal(order.getUserId());
        // 处理商品销量
//        if (!CollectionUtils.isEmpty(orderSkuList)) {
//            Map<Integer, Integer> skuBuyNumMap = Maps.newHashMap();
//            for (OrderSku orderSku : orderSkuList) {
//                Integer skuBuyNum = skuBuyNumMap.get(orderSku.getSkuId());
//                if (skuBuyNum == null) {
//                    skuBuyNum = orderSku.getBuyNum();
//                } else {
//                    skuBuyNum = skuBuyNum + orderSku.getBuyNum();
//                }
//                skuBuyNumMap.put(orderSku.getSkuId(), skuBuyNum);
//            }
//            skuInfoService.skuSaleNumDeal(skuBuyNumMap);
//        }
    }

    @Override
    public IPage<SearchOrderRespVo> pageSearchSkuOrderInfo(SearchSkuOrderDto searchSkuOrderDto) {

        IPage<SearchOrderRespVo> searchOrderRespVoPage = new Page<>();
        if (searchSkuOrderDto == null) {
            return searchOrderRespVoPage;
        }
        int userId = 0;
        if (!StringUtils.isEmpty(searchSkuOrderDto.getToken())) {
            userId = (int) JwtUtils.parseJWT(searchSkuOrderDto.getToken()).get("id");
        }
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (searchSkuOrderDto.getUserId() != null) {
            wrapper.eq("user_id", searchSkuOrderDto.getUserId());
        }
        if (searchSkuOrderDto.getStatus() != null) {
            wrapper.eq("status", searchSkuOrderDto.getStatus());
        }
        if (!StringUtils.isEmpty(searchSkuOrderDto.getOrderNo())) {
            wrapper.eq("order_no", searchSkuOrderDto.getOrderNo());
        }
        wrapper.orderByDesc("id");
        IPage<Order> orderPage = baseMapper.selectPage(new Page<>(searchSkuOrderDto.getCurrentPage(), searchSkuOrderDto.getPageSize()), wrapper);
        if (orderPage != null) {
            BeanUtils.copyProperties(orderPage, searchOrderRespVoPage);
            if (!CollectionUtils.isEmpty(orderPage.getRecords())) {
                List<SearchOrderRespVo> searchOrderRespVoList = Lists.newArrayList();
                int finalUserId = userId;
                orderPage.getRecords().forEach(e -> {
                    SearchOrderRespVo searchOrderRespVo = convertOrderRespVoFromOrder(e);
                    if (finalUserId != 0) {
                        Integer count = invoiceUserService.lambdaQuery().eq(InvoiceUser::getUserId, finalUserId)
                                .eq(InvoiceUser::getOrderId, e.getId()).count();
                        searchOrderRespVo.setStatusInvoice(count > 0 ? 1 : 0);
                    }
                    searchOrderRespVoList.add(searchOrderRespVo);
                });
                searchOrderRespVoPage.setRecords(searchOrderRespVoList);
            }
        }
        return searchOrderRespVoPage;
    }

    @Override
    public Order getSkuOrderInfoByOrderNo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return null;
        }
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public IPage<SearchOrderRespVo> backPageSearchSkuOrderInfo(BackSearchOrderDto backSearchOrderDto) {
        IPage<SearchOrderRespVo> searchOrderRespVoPage = new Page<>();
        if (backSearchOrderDto == null) {
            return searchOrderRespVoPage;
        }
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (backSearchOrderDto.getUserId() != null) {
            wrapper.eq("user_id", backSearchOrderDto.getUserId());
        }
        if (!StringUtils.isEmpty(backSearchOrderDto.getCreateTimeStart())) {
            wrapper.ge("create_time", backSearchOrderDto.getCreateTimeStart());
        }
        if (!StringUtils.isEmpty(backSearchOrderDto.getCreateTimeEnd())) {
            wrapper.le("create_time", backSearchOrderDto.getCreateTimeEnd());
        }
        if (!StringUtils.isEmpty(backSearchOrderDto.getOrderNo())) {
            wrapper.eq("order_no", backSearchOrderDto.getOrderNo());
        }
        if (backSearchOrderDto.getStatus() != null) {
            wrapper.eq("status", backSearchOrderDto.getStatus());
        }
        if (backSearchOrderDto.getPayWay() != null) {
            wrapper.eq("pay_way", backSearchOrderDto.getPayWay());
        }
        wrapper.orderByDesc("create_time");
        wrapper.orderByDesc("id");
        IPage<Order> orderPage = baseMapper.selectPage(new Page<>(backSearchOrderDto.getCurrentPage(), backSearchOrderDto.getPageSize()), wrapper);
        if (orderPage != null) {
            BeanUtils.copyProperties(orderPage, searchOrderRespVoPage);
            if (!CollectionUtils.isEmpty(orderPage.getRecords())) {
                List<SearchOrderRespVo> searchOrderRespVoList = orderPage.getRecords().stream().map(order -> {
                    SearchOrderRespVo searchOrderRespVo = convertOrderRespVoFromOrder(order);
                    Integer count = invoiceUserService.lambdaQuery()
                            .eq(InvoiceUser::getOrderId, order.getId()).count();
                    searchOrderRespVo.setStatusInvoice(count > 0 ? 1 : 0);
                    return searchOrderRespVo;
                }).collect(Collectors.toList());
                searchOrderRespVoPage.setRecords(searchOrderRespVoList);
            }
        }
        return searchOrderRespVoPage;
    }

    @Override
    @Transactional
    public int updateOrderStatus(UpdateOrderStatusDto updateOrderStatusDto) {
        if (StringUtils.isEmpty(updateOrderStatusDto.getOrderNo())) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单号为空!");
        }
        if (OrderSkuStatus.valueOf(updateOrderStatusDto.getStatus()) == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单状态不正确!");
        }
        Order order = getSkuOrderInfoByOrderNo(updateOrderStatusDto.getOrderNo());
        if (order == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单不存在!");
        }
        order.setStatus(updateOrderStatusDto.getStatus());
        int orderResult = baseMapper.updateById(order);
        if (orderResult <= 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单状态修改失败!");
        }
        List<OrderSku> orderSkuList = orderSkuService.findSkuListByOrderNo(updateOrderStatusDto.getOrderNo());
        if (!CollectionUtils.isEmpty(orderSkuList)) {
            orderSkuList.forEach(e -> {
                e.setStatus(updateOrderStatusDto.getStatus());
            });
            boolean orderSkuRes = orderSkuService.updateBatchById(orderSkuList);
            if (!orderSkuRes) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单商品状态修改失败!");
            }
        }
//        if (updateOrderStatusDto.getStatus() == OrderStatus.OVER.getCode()) {
//            // 发送商品收货通知
//            try {
//                SendLetterInput sendLetterInput = new SendLetterInput();
//                sendLetterInput.setUserIds(Lists.newArrayList(order.getUserId()));
//                Map<String, Object> paramMap = Maps.newHashMap();
//                paramMap.put("orderNo", order.getOrderNo());
//                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                paramMap.put("receiveTime", df.format(LocalDateTime.now()));
//                sendLetterInput.setTriggerPoint(3);
//                sendLetterInput.setMessages(paramMap);
//                boolean messageResult = letterTemplateService.send(sendLetterInput);
//                if (messageResult != Boolean.TRUE) {
//                    log.error("【messageLetterDeal】 发送消息失败,orderNo:{},", order.getOrderNo());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("【messageLetterDeal】 发送消息失败,orderNo:{}", order.getOrderNo());
//            }
//        }
        return orderResult;
    }

    @Override
    public Page<ServerSearchOrderRespVo> serverPageSearchSkuOrderInfo(ServerBatchSearchOrderDto searchOrderDto) {
        Page<ServerSearchOrderRespVo> searchOrderRespVoPage = new Page<>();
        if (searchOrderDto == null) {
            return searchOrderRespVoPage;
        }
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(searchOrderDto.getUserIdList())) {
            wrapper.in("user_id", searchOrderDto.getUserIdList());
        }
        if (!StringUtils.isEmpty(searchOrderDto.getCreateTimeStart())) {
            wrapper.gt("create_time", searchOrderDto.getCreateTimeStart());
        }
        if (!StringUtils.isEmpty(searchOrderDto.getCreateTimeEnd())) {
            wrapper.lt("create_time", searchOrderDto.getCreateTimeEnd());
        }
        if (searchOrderDto.getStatus() != null) {
            wrapper.eq("status", searchOrderDto.getStatus());
        }
        if (searchOrderDto.getPayWay() != null) {
            wrapper.eq("pay_way", searchOrderDto.getPayWay());
        }
        wrapper.orderByDesc("id");

        IPage<Order> orderPage = baseMapper.selectPage(new Page<>(searchOrderDto.getCurrentPage(), searchOrderDto.getPageSize()), wrapper);
        if (orderPage != null) {
            BeanUtils.copyProperties(orderPage, searchOrderRespVoPage);
            if (!CollectionUtils.isEmpty(orderPage.getRecords())) {
                List<ServerSearchOrderRespVo> searchOrderRespVoList = orderPage.getRecords().stream().map(order -> {
                    ServerSearchOrderRespVo searchOrderRespVo = convertServerOrderRespVoFromOrder(order);
                    return searchOrderRespVo;
                }).collect(Collectors.toList());
                searchOrderRespVoPage.setRecords(searchOrderRespVoList);
            }
        }
        return searchOrderRespVoPage;
    }

    @Override
    public boolean updateOrderSkuStatus(UpdateOrderSkuStatusDto updateOrderSkuStatusDto) {
        if (CollectionUtils.isEmpty(updateOrderSkuStatusDto.getIds())) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品ID为空!");
        }
        if (OrderSkuStatus.valueOf(updateOrderSkuStatusDto.getStatus()) == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单商品状态不正确!");
        }
        List<OrderSku> orderSkuList = (List<OrderSku>) orderSkuService.listByIds(updateOrderSkuStatusDto.getIds());
        if (!CollectionUtils.isEmpty(orderSkuList)) {
            orderSkuList.forEach(e -> {
                e.setStatus(updateOrderSkuStatusDto.getStatus());
            });
        }
        return orderSkuService.updateBatchById(orderSkuList);
    }

    @Override
    public CommitOrderRespVo gotoPay(GotoPayDto gotoPayDto) {
        if (StringUtils.isEmpty(gotoPayDto.getOrderNo())) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单号为空!");
        }
        Order order = orderService.getSkuOrderInfoByOrderNo(gotoPayDto.getOrderNo());
        if (order == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单不存在!");
        }
        if (order.getStatus() != OrderStatus.WAIT_PAY.getCode()) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单状态不是待支付状态");
        }
        // 请求微信接口获取小程序支付相关信息
        String openId = JwtUtils.parseJWT(gotoPayDto.getToken()).get("openid").toString();
        CommitOrderRespVo commitOrderRespVo = new CommitOrderRespVo();
        wxPayRequest(gotoPayDto.getIp(), openId, order.getOrderNo(), order.getAmount(), commitOrderRespVo);
        if (StringUtils.isEmpty(commitOrderRespVo.getPrepayId())) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "生成预支付id失败");
        }
        // 保存订单交易日志表
        saveOrderPayLog(order.getUserId(), openId, order.getOrderNo(), commitOrderRespVo.getPrepayId());
        commitOrderRespVo.setOrderNo(order.getOrderNo());
        return commitOrderRespVo;
    }

    @Override
    public SearchOrderRespVo searchOrderDetail(SearchOrderDetailDto searchOrderDetailDto) {
        if (StringUtils.isEmpty(searchOrderDetailDto.getOrderNo())) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单号为空");
        }
        Order order = getSkuOrderInfoByOrderNo(searchOrderDetailDto.getOrderNo());
        int userId = (int) JwtUtils.parseJWT(searchOrderDetailDto.getToken()).get("id");
        if (order != null) {
            if (order.getUserId() != userId) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "该订单不属于当前登录人");
            }
        }
        return convertOrderRespVoFromOrder(order);
    }

    @Override
    public StatisticalOrderRespVo statisticalOrder(StatisticalOrderDto statisticalOrderDto) {
        StatisticalOrderRespVo statisticalOrderRespVo = new StatisticalOrderRespVo();
        List<Integer> userIdList = statisticalOrderDto.getUserIdList();
        if (CollectionUtils.isEmpty(userIdList)) {
            return statisticalOrderRespVo;
        }
        Map<Integer, StatisticalOrderInfo> statisticalMap = new HashMap<>();
        List<StatisticalOrderInfo> statisticalList = orderMapper.statisticalOrderAmountAndNum(userIdList, statisticalOrderDto.getPayWay());
        if (!CollectionUtils.isEmpty(statisticalList)) {
            statisticalMap = statisticalList.stream().collect(Collectors.toMap(StatisticalOrderInfo::getUserId, statisticalOrderInfo -> statisticalOrderInfo));
        }
        statisticalOrderRespVo.setStatisticalOrderMap(statisticalMap);
        return statisticalOrderRespVo;
    }

    @Override
    public CounselorStatisticalOrderRespVo counselorStatisticalOrder(CounselorStatisticalOrderDto statisticalOrderDto) {
        CounselorStatisticalOrderRespVo statisticalOrderRespVo = new CounselorStatisticalOrderRespVo();
        List<Integer> counselorIdList = statisticalOrderDto.getCounselorIdList();
        if (CollectionUtils.isEmpty(counselorIdList)) {
            return statisticalOrderRespVo;
        }
        Map<Integer, CounselorStatisticalOrderInfo> statisticalMap = new HashMap<>();
        List<CounselorStatisticalOrderInfo> statisticalList = orderMapper.statisticalCounselorOrderAmountAndNum(counselorIdList, statisticalOrderDto.getPayWay());
        if (!CollectionUtils.isEmpty(statisticalList)) {
            statisticalMap = statisticalList.stream().collect(Collectors.toMap(CounselorStatisticalOrderInfo::getCounselorId, statisticalOrderInfo -> statisticalOrderInfo));
        }
        statisticalOrderRespVo.setStatisticalOrderMap(statisticalMap);
        return statisticalOrderRespVo;
    }

    @Override
    public ConsumeRecordRespVo consumeRecord(ConsumeRecordDto consumeRecordDto) {
        ConsumeRecordRespVo consumeRecordRespVo = new ConsumeRecordRespVo();
        if (consumeRecordDto == null) {
            return consumeRecordRespVo;
        }
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        int userId = (int) JwtUtils.parseJWT(consumeRecordDto.getToken()).get("id");
        consumeRecordDto.setUserId(userId);
        orderQueryWrapper.eq("user_id", userId);
        orderQueryWrapper.eq("status", OrderStatus.OVER.getCode());
        if (consumeRecordDto.getPayWay() != null) {
            orderQueryWrapper.eq("pay_way", consumeRecordDto.getPayWay());
        }
        if (!StringUtils.isEmpty(consumeRecordDto.getStartTime())) {
            orderQueryWrapper.ge("pay_time", LocalDateTimeUtils.localTime(consumeRecordDto.getStartTime() + " 00:00:00"));
        }
        if (!StringUtils.isEmpty(consumeRecordDto.getEndTime())) {
            orderQueryWrapper.le("pay_time", LocalDateTimeUtils.localTime(consumeRecordDto.getEndTime() + " 23:59:59"));
        }
        IPage<Order> orderPage = baseMapper.selectPage(new Page<>(consumeRecordDto.getCurrentPage(), consumeRecordDto.getPageSize()), orderQueryWrapper);
        if (orderPage != null) {
            IPage<SearchOrderRespVo> orderRespVoPage = new Page<>();
            BeanUtils.copyProperties(orderPage, orderRespVoPage);
            if (!CollectionUtils.isEmpty(orderPage.getRecords())) {
                List<SearchOrderRespVo> searchOrderRespVoList = Lists.newArrayList();
                orderPage.getRecords().forEach(e -> {
                    SearchOrderRespVo searchOrderRespVo = convertOrderRespVoFromOrder(e);
                    searchOrderRespVoList.add(searchOrderRespVo);
                });
                orderRespVoPage.setRecords(searchOrderRespVoList);

                // 统计金额和积分
                ConsumeRecordQueryInfo consumeRecordQueryInfo = new ConsumeRecordQueryInfo();
                BeanUtils.copyProperties(consumeRecordDto, consumeRecordQueryInfo);
                ConsumeRecordInfo statisticalVo = orderMapper.statisticalOrderAmountAndIntegral(consumeRecordQueryInfo);
                if (statisticalVo != null) {
                    BeanUtils.copyProperties(statisticalVo, consumeRecordRespVo);
                }
            }
            consumeRecordRespVo.setOrderPageInfo(orderRespVoPage);
        }

        return consumeRecordRespVo;
    }

    @Override
    public BackSearchOrderRespVo getSkuOrderInfoById(Integer id) {
        if (id == null) {
            return null;
        }
        Order order = baseMapper.selectById(id);
        if (order == null) {
            return null;
        }
        BackSearchOrderRespVo backSearchOrderRespVo = new BackSearchOrderRespVo();
        SearchOrderRespVo searchOrderRespVo = convertOrderRespVoFromOrder(order);
        BeanUtils.copyProperties(searchOrderRespVo, backSearchOrderRespVo);
        // 查询物流信息
        SearchOrderDetailDto searchOrderDetailDto = new SearchOrderDetailDto();
        searchOrderDetailDto.setOrderNo(order.getOrderNo());
//        List<SearchOrderLogisticsRespVo> orderLogisticsRespVoList = orderLogisticsService.searchOrderLogisticsList(searchOrderDetailDto);
//        backSearchOrderRespVo.setOrderLogisticsRespVoList(orderLogisticsRespVoList);
        return backSearchOrderRespVo;
    }

    @Override
    public boolean updateExpressStatus(UpdateOrderExpressStatusDto updateOrderExpressStatusDto) {
        if (CollectionUtils.isEmpty(updateOrderExpressStatusDto.getIds())) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品ID为空!");
        }
        boolean isExpress = updateOrderExpressStatusDto.getExpressStatus() == ExpressStatus.EXPRESSED.getCode();
        if (isExpress) {
            if (updateOrderExpressStatusDto.getExpressType() <= 0 || StringUtils.isEmpty(updateOrderExpressStatusDto.getExpressNo())) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "快递信息为空!");
            }
        }
        List<OrderSku> orderSkuList = (List<OrderSku>) orderSkuService.listByIds(updateOrderExpressStatusDto.getIds());
        if (CollectionUtils.isEmpty(orderSkuList) || orderSkuList.size() != updateOrderExpressStatusDto.getIds().size()) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品id有误!");
        }
        LocalDateTime now = now();
        // 批量插入物流表
        List<OrderLogistics> orderLogisticsList = orderSkuList.stream().map(orderSku -> {
            OrderLogistics orderLogistics = new OrderLogistics();
            orderLogistics.setExpressNo(updateOrderExpressStatusDto.getExpressNo());
            orderLogistics.setCreateTime(now);
            orderLogistics.setExpressType(updateOrderExpressStatusDto.getExpressType());
            orderLogistics.setOrderNo(orderSku.getOrderNo());
            orderLogistics.setSkuId(orderSku.getSkuId());
            orderLogistics.setUserId(orderSku.getUserId());
            orderSku.setExpressStatus(ExpressStatus.EXPRESSED.getCode());
            orderSku.setExpressTime(now);
            orderSku.setExpressNo(updateOrderExpressStatusDto.getExpressNo());
            orderSku.setExpressType(updateOrderExpressStatusDto.getExpressType());
            return orderLogistics;
        }).collect(Collectors.toList());
        boolean batchSaveRes = orderLogisticsService.saveBatch(orderLogisticsList);
        if (!batchSaveRes) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "保存物流信息失败!");
        }
        boolean updateOrderSkuStatus = orderSkuService.updateBatchById(orderSkuList);
        if (!updateOrderSkuStatus) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "更新订单产品发货状态失败!");
        }
        for (OrderLogistics orderLogistics : orderLogisticsList) {
            // 发送商品发货通知
            try {
                SkuInfo skuInfo = skuInfoService.getById(orderLogistics.getSkuId());
                SendLetterInput sendLetterInput = new SendLetterInput();
                sendLetterInput.setUserIds(Lists.newArrayList(orderLogistics.getUserId()));
                Map<String, Object> paramMap = Maps.newHashMap();
                paramMap.put("orderNo", orderLogistics.getOrderNo());
                paramMap.put("skuName", skuInfo != null ? skuInfo.getSkuName() : "");
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                paramMap.put("expressTime", df.format(orderLogistics.getCreateTime()));
                paramMap.put("expressName", ExpressCompanyEnum.valueOf(orderLogistics.getExpressType()).getDescription());
                paramMap.put("expressNo", orderLogistics.getExpressNo());
                sendLetterInput.setTriggerPoint(2);
                sendLetterInput.setMessages(paramMap);
                boolean messageResult = letterTemplateService.send(sendLetterInput);
                if (messageResult != Boolean.TRUE) {
                    log.error("【messageLetterDeal】 发送消息失败,orderNo:{},skuId:{}", orderLogistics.getOrderNo(), orderLogistics.getSkuId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("【messageLetterDeal】 发送消息失败,orderNo:{},skuId:{}", orderLogistics.getOrderNo(), orderLogistics.getSkuId());
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public int statisticalOrderCount() {
        return baseMapper.statisticalOrderCount();
    }

    @Override
    public BigDecimal statisticalOrderAmount() {
        return baseMapper.statisticalOrderAmount();
    }

    @Override
    public List<StatisticalMonthOrderCount> statisticalMonthOrderCount(String startDate, String endDate) {
        return baseMapper.statisticalMonthOrderCount(startDate, endDate);
    }

    @Override
    public HomeOrderStatisticalRespVo homeOrderAndSkuStatistical(String date) {
        int skuCount = skuInfoService.statisticalSkuCount();
        int orderCount = orderService.statisticalOrderCount();
        BigDecimal orderAmount = orderService.statisticalOrderAmount();

        String startDate = date + "-01 00:00:00";
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
        //设置月份
        cal.set(Calendar.MONTH, Integer.parseInt(date.split("-")[1]) - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = sdf.format(cal.getTime()) + " 23:59:59";

        Map<String, Integer> dayOrderCountMap = new HashMap<>();

        List<StatisticalMonthOrderCount> dayOrderCountList = orderService.statisticalMonthOrderCount(startDate, endDate);
        if (!CollectionUtils.isEmpty(dayOrderCountList)) {
            dayOrderCountMap = dayOrderCountList.stream().collect(Collectors.toMap(StatisticalMonthOrderCount::getOrderDay, a -> a.getOrderCount()));
        }

        HomeOrderStatisticalRespVo respVo = new HomeOrderStatisticalRespVo();
        respVo.setOrderAmount(orderAmount);
        respVo.setOrderCount(orderCount);
        respVo.setSkuCount(skuCount);
        respVo.setMonthOrderCountMap(dayOrderCountMap);

        return respVo;
    }

    /*@Override
    public boolean updateFetchStatus(UpdateOrderFetchStatusDto updateOrderFetchStatusDto) {
        if(!CollectionUtils.isEmpty(updateOrderFetchStatusDto.getIds())){
            throw new SepCustomException(ResponseData.STATUS_CODE_400,"商品ID为空!");
        }
        List<Order> orderList = baseMapper.selectBatchIds(updateOrderFetchStatusDto.getIds());
        if(!CollectionUtils.isEmpty(orderList)){
            orderList.forEach(e->{
                e.setFetchStatus(updateOrderFetchStatusDto.getFetchStatus());
            });
        }
        return updateBatchById(orderList);
    }*/

    private void increasePointDeal(Integer userId) {
        try {
            PointIncreaseInput pointIncreaseInput = new PointIncreaseInput();
            pointIncreaseInput.setUserId(userId);
            pointIncreaseInput.setFundChangeType(7);
            boolean pointResult = pointService.increase(pointIncreaseInput);
            if (pointResult != Boolean.TRUE) {
                log.error("【increasePointDeal】 积分增加失败,userId:{}", userId);
            }
        } catch (Exception e) {
            log.error("【increasePointDeal】积分增加失败!");
        }
    }

    private void messageLetterDeal(Integer userId, Order order) {
        try {
            log.info("进入发送消息方法...........................");
            SendLetterInput sendLetterInput = new SendLetterInput();
            sendLetterInput.setUserIds(Lists.newArrayList(userId));
            // 下单成功是0，支付成功是1
            int triggerPoint = 0;
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("orderNo", order.getOrderNo());
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (order.getStatus() == OrderStatus.OVER.getCode()) {
                paramMap.put("payTime", df.format(order.getPayTime()));
                triggerPoint = 1;
            } else {
                paramMap.put("orderTime", df.format(order.getCreateTime()));
            }
            sendLetterInput.setTriggerPoint(triggerPoint);
            sendLetterInput.setMessages(paramMap);

            log.info("消息发送封装结果...........................{}",sendLetterInput);
            boolean messageResult = letterTemplateService.send(sendLetterInput);
            if (messageResult != Boolean.TRUE) {
                log.error("【messageLetterDeal】 发送消息失败,orderNo:{}", order.getOrderNo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【messageLetterDeal】 发送消息失败,orderNo:{}", order.getOrderNo());
        }

    }

    /**
     * 积分消费处理
     *
     * @param orderId
     * @param orderNo
     * @param totalIntegralNum
     * @param userId
     */
    private void pointConsumeDeal(Integer orderId, String orderNo, int totalIntegralNum, int userId) {
        try {
            ProductsExchangeInput productsExchangeInput = new ProductsExchangeInput();
            productsExchangeInput.setOrderId(orderId);
            productsExchangeInput.setOrderNo(orderNo);
            productsExchangeInput.setTakePoint(totalIntegralNum);
            productsExchangeInput.setUserId(userId);
            log.info("【commitOrder】,pointConsumeDeal,productsExchangeInput:{}", JSON.toJSONString(productsExchangeInput));
            boolean pointResult = pointService.productsExchange(productsExchangeInput);
            if (pointResult != Boolean.TRUE) {
                log.error("【commitOrder】-> pointResult : {}", JSON.toJSONString(pointResult));
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "积分扣减失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "积分扣减失败");
        }
    }

    private void cartDeal(List<String> skuUniquKeyList, String token) {
        try {
            BatchDeleteSkuCartDto batchDeleteSkuCartDto = new BatchDeleteSkuCartDto();
            batchDeleteSkuCartDto.setSkuUniqueKeyList(skuUniquKeyList);
            batchDeleteSkuCartDto.setToken(token);
            cartService.batchDeleteSku(batchDeleteSkuCartDto);
        } catch (Exception e) {
            log.error("【cartDeal】-> cartDeal has exception:{}", e);
        }

    }

    private void useCouponDeal(Integer couponId, Integer userId, BigDecimal originTotalAmount, BigDecimal totalAmount, Integer orderId, String orderNo) {
        log.info("【commitOrder】,useCouponDeal,orderId:{}", orderId);
        // 调用优惠券接口，修改优惠券状态
        try {
            UseCouponInput useCouponInput = new UseCouponInput();
            useCouponInput.setCouponId(couponId);
            useCouponInput.setConsumer(userId);
            useCouponInput.setConsumeTime(LocalDateTime.now());
            useCouponInput.setMonetary(originTotalAmount);
            useCouponInput.setActualMonetary(totalAmount);
            useCouponInput.setOrderId(orderId);
            useCouponInput.setOrderNo(orderNo);
            log.info("【commitOrder】,useCouponDeal,useCouponInput:{}", JSON.toJSONString(useCouponInput));
//            boolean useCouponResp = couponService.use(useCouponInput);
//            if (useCouponResp != Boolean.TRUE) {
//                log.error("【useCouponDeal】优惠券使用失败,useCouponResp:{}", JSON.toJSONString(useCouponResp));
//                throw new SepCustomException(ResponseData.STATUS_CODE_400, "优惠券使用失败");
//            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "优惠券使用失败");
        }
    }

    /**
     * 分销返现处理
     *
     * @param order
     * @param payTime
     */
    private void cashBackDeal(Order order, LocalDateTime payTime) {
        // todo 只有参与分销的商品才能返现
        try {
            CashbackDto cashbackDto = new CashbackDto();
            cashbackDto.setConsumeTime(payTime);
            cashbackDto.setConsumer(order.getUserId());
            cashbackDto.setMonetary(order.getAmount());
            cashbackDto.setOrderId(order.getId());
            cashbackDto.setOrderNo(order.getOrderNo());
            boolean cashbackRes = cashBackService.cashback(cashbackDto);
            if (cashbackRes != Boolean.TRUE) {
                log.error("【wxNotifyDeal】 分销业务处理失败,orderNo:{}", order.getOrderNo());
            }
        } catch (Exception e) {
            log.error("【wxNotifyDeal】-> cashBackDeal has exception:{}", e);
        }

    }

    private void saveOrderPayLog(Integer userId, String openId, String orderNo, String prepayId) {
        try {
            OrderPayLog orderPayLog = new OrderPayLog();
            orderPayLog.setUserId(userId);
            orderPayLog.setOpenId(openId);
            orderPayLog.setOrderNo(orderNo);
            orderPayLog.setWxPrepayId(prepayId);
            orderPayLog.setCreateTime(now());
            boolean payLogResult = orderPayLogService.save(orderPayLog);
            if (!payLogResult) {
                log.error("【commitOrder】-> saveOrderPayLog fail,result:{}", payLogResult);
            }
        } catch (Exception e) {
            log.error("【commitOrder】-> saveOrderPayLog has exception:{}", e);
        }
    }

    private void wxPayRequest(String ip, String openId, String orderNo, BigDecimal totalAmount, CommitOrderRespVo commitOrderRespVo) {
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
                commitOrderRespVo.setPrepayId(prepay_id);
                commitOrderRespVo.setNonceStr(nonce_str);
                commitOrderRespVo.setWpackage("prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                commitOrderRespVo.setTimeStamp(timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                //拼接签名需要的参数
                String stringSignTemp = "appId=" + wxPayConfig.appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = Paytool.sign(stringSignTemp, wxPayConfig.key, "utf-8").toUpperCase();

                commitOrderRespVo.setPaySign(paySign);
            }
            commitOrderRespVo.setAppId(wxPayConfig.appid);
            log.info("【commitOrder】->commitOrderRespVo : {}", JSON.toJSONString(commitOrderRespVo));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "调取微信支付失败");
        }
    }

    /**
     * 递归生成订单唯一编号
     * 规则：年份+10位随机数，年份有利于后期做分表
     *
     * @return
     */
    private String getNewOrderNo() {
        String random = RandomStringUtils.random(10, false, true);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//获取年
        String orderNo = year + "" + random;
        SearchSkuOrderDto searchSkuOrderDto = new SearchSkuOrderDto();
        searchSkuOrderDto.setOrderNo(orderNo);
        IPage<SearchOrderRespVo> orderRespVoPage = orderService.pageSearchSkuOrderInfo(searchSkuOrderDto);
        if (orderRespVoPage != null && orderRespVoPage.getTotal() > 0) {
            return getNewOrderNo();
        }
        return orderNo;
    }

    private Order saveOrderAndSku(String orderNo,String remarks, CommitOrderDto commitOrderDto, Map<String, Integer> skuBuyNumMap, Integer userId, String unionId,
                                  Map<String, SearchSkuRespVo> skuRespVoMap, Map<String, List<SettlementPropertyInfo>> skuPropertyInfoMap, BigDecimal originTotalAmount,
                                  BigDecimal totalAmount, int totalIntegralNum, Map<String, CouponValuationRespSkuInfo> couponValucationSkuInfoMap) {

        int status = (commitOrderDto.getPayWay() == PayWayEnum.INTEGRAL_PAY.getCode() || totalAmount.compareTo(BigDecimal.ZERO) <= 0)
                ? OrderStatus.OVER.getCode() : OrderStatus.WAIT_PAY.getCode();
        LocalDateTime createTime = now();
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setUnionId(unionId);
        order.setStatus(status);
        order.setRemarks(remarks);
        if (status == OrderStatus.OVER.getCode()) {
            order.setPayTime(createTime);
        }
//        order.setCouponId(couponId);
        if (commitOrderDto.getPayWay() == PayWayEnum.WEIXIN_PAY.getCode()) {
            order.setOriginAmount(originTotalAmount);
            order.setAmount(totalAmount);
            order.setCouponDiscountAmount(originTotalAmount.subtract(totalAmount));
        }
        if (commitOrderDto.getPayWay() == PayWayEnum.INTEGRAL_PAY.getCode()) {
            order.setIntegralNum(totalIntegralNum);
        }
        order.setTakeInfoId(commitOrderDto.getTakeInfoId());
        order.setPayWay(commitOrderDto.getPayWay());
        order.setCreateTime(createTime);

//        if (!CollectionUtils.isEmpty(commitOrderDto.getBuySkuList()) && skuRespVoMap.size() == 1) {
//            String remark = null;
//            Integer zdwId = null;
//            for (String uniquKey : skuRespVoMap.keySet()) {
//                if (!StringUtils.isEmpty(skuRespVoMap.get(uniquKey).getEncryptParam())) {
//                    ZdwCouponDto zdwCouponDto = new ZdwCouponDto();
//                    try {
//                        zdwCouponDto = settlementService.getZdwCouponDto(skuRespVoMap.get(uniquKey).getEncryptParam());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    remark = settlementService.getZdwRemark(zdwCouponDto);
//                    zdwId = zdwCouponDto.getOrderSkuId();
//                }
//            }
//            if (!StringUtils.isEmpty(remark) && zdwId != null) {
//                order.setZdwRemark(remark);
//                order.setZdwOrderSkuId(zdwId);
//            }
//        }

        // 插入订单表
        int orderResult = baseMapper.insert(order);
        if (orderResult <= 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单插入失败");
        }
        Map<Integer, List<SettlementPropertyInfo>> skuPropertyByOrderSkuIdMap = Maps.newHashMap();
        if (!Objects.isNull(commitOrderDto.getSkuId())) {
            for (String uniquKey : skuRespVoMap.keySet()) {
                OrderSku orderSku = new OrderSku();
                orderSku.setOrderNo(orderNo);
                orderSku.setUserId(userId);
                orderSku.setUnionId(unionId);
                orderSku.setStatus(status);
                orderSku.setSkuId(skuRespVoMap.get(uniquKey).getId());
                orderSku.setBuyNum(skuBuyNumMap.get(uniquKey));
                orderSku.setOriginPrice(skuRespVoMap.get(uniquKey).getCurrentPrice());
                if (couponValucationSkuInfoMap.get(uniquKey) != null) {
                    orderSku.setSkuPrice(couponValucationSkuInfoMap.get(uniquKey).getCouponPrice());
                } else {
                    orderSku.setSkuPrice(skuRespVoMap.get(uniquKey).getCurrentPrice());
                }
//                if (!StringUtils.isEmpty(skuRespVoMap.get(uniquKey).getEncryptParam())) {
//                    ZdwCouponDto zdwCouponDto = new ZdwCouponDto();
//                    try {
//                        zdwCouponDto = settlementService.getZdwCouponDto(skuRespVoMap.get(uniquKey).getEncryptParam());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (zdwCouponDto != null) {
//                        orderSku.setZdwOrderSkuId(zdwCouponDto.getOrderSkuId());
//                        orderSku.setZdwRemark(settlementService.getZdwRemark(zdwCouponDto));
//                    }
//                }
                orderSku.setCreateTime(createTime);
                orderSku.setPayWay(commitOrderDto.getPayWay());
                if (commitOrderDto.getPayWay() == PayWayEnum.INTEGRAL_PAY.getCode()) {
                    orderSku.setSkuIntegral(skuRespVoMap.get(uniquKey).getIntegralNum());
                }
                /// 插入订单商品表
                boolean orderSkuResult = orderSkuService.save(orderSku);
                if (!orderSkuResult) {
                    throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单商品插入失败");
                }
                List<SettlementPropertyInfo> propertyInfoList = skuPropertyInfoMap.get(uniquKey);
                if (!CollectionUtils.isEmpty(propertyInfoList)) {
                    skuPropertyByOrderSkuIdMap.put(orderSku.getId(), propertyInfoList);
                }
            }
        }
        // 插入订单商品属性表
        List<OrderSkuProperty> orderSkuPropertyList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(skuPropertyByOrderSkuIdMap)) {
            for (Integer orderSkuId : skuPropertyByOrderSkuIdMap.keySet()) {
                List<SettlementPropertyInfo> propertyInfoList = skuPropertyByOrderSkuIdMap.get(orderSkuId);
                if (!CollectionUtils.isEmpty(propertyInfoList)) {
                    for (SettlementPropertyInfo settlementPropertyInfo : propertyInfoList) {
                        OrderSkuProperty orderSkuProperty = new OrderSkuProperty();
                        BeanUtils.copyProperties(settlementPropertyInfo, orderSkuProperty);
                        orderSkuProperty.setOrderSkuId(orderSkuId);
                        orderSkuProperty.setCreateTime(createTime);
                        orderSkuPropertyList.add(orderSkuProperty);
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(orderSkuPropertyList)) {
            boolean orderSkuPropertyResult = orderSkuPropertyService.saveBatch(orderSkuPropertyList);
            if (!orderSkuPropertyResult) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "订单商品属性插入失败");
            }
        }
        return order;
    }

    private SearchOrderRespVo convertOrderRespVoFromOrder(Order order) {

        if (order == null) {
            return null;
        }
        SearchOrderRespVo searchOrderRespVo = new SearchOrderRespVo();
        BeanUtils.copyProperties(order, searchOrderRespVo);
        // 查询收货信息
        GetUserAddressInput getUserAddressInput = new GetUserAddressInput();
        getUserAddressInput.setId(order.getTakeInfoId());
//        AddressVo addressVo = addressService.getDetails(order.getTakeInfoId());
//        searchOrderRespVo.setAddressVo(addressVo);

        // 支付方式名称
        searchOrderRespVo.setPayWayName(PayWayEnum.valueOf(order.getPayWay()).getDescription());
        // 订单状态名称
        OrderStatus orderStatus = OrderStatus.valueOf(order.getStatus());
        searchOrderRespVo.setStatusName(orderStatus.getDescription());
        searchOrderRespVo.setStatusCode(order.getStatus());
//        if (!Objects.isNull(order.getZdwOrderSkuId()) && order.getZdwOrderSkuId() > 0) {
//            String data = HttpClient4.doGet(zdwGetSkuId + order.getZdwOrderSkuId());
//            JSONObject json = JSONObject.parseObject(data);
//            if (json != null && json.containsKey("code") && json.getInteger("code").equals(200) && json.containsKey("data")) {
//                searchOrderRespVo.setZdwOrderSkuId(json.getInteger("data"));
//            }
//        }
        // 查询订单商品
        List<OrderInformation> orderSkuList = orderInformation.findSkuListByOrderNo(order.getOrderNo());
        int skuTotalCount = 0;
        int noExpressCount = 0; // 待发货商品数量
        if (!CollectionUtils.isEmpty(orderSkuList)) {
            skuTotalCount = orderSkuList.size();
            noExpressCount = orderSkuList.size();
            List<String> collect = orderSkuList.stream().map(OrderInformation::getPhone).collect(Collectors.toList());
            searchOrderRespVo.setPhones(collect);
            if (order.getStatus() == 1) {
                searchOrderRespVo.setConsumableCode(orderSkuList.get(0).getConsumableCode());
            }
            searchOrderRespVo.setSkuTotalCount(skuTotalCount);
            SkuInfo one = skuInfoService.lambdaQuery().eq(SkuInfo::getId, orderSkuList.get(0).getSkuId()).one();
            if (one != null) {
                searchOrderRespVo.setSkuName(one.getSkuName());
                BigDecimal bigDecimal = new BigDecimal(skuTotalCount);
                BigDecimal multiply = one.getCurrentPrice().multiply(bigDecimal);
                searchOrderRespVo.setTotalAmount(multiply);

                searchOrderRespVo.setOriginPrice(one.getCurrentPrice());
                searchOrderRespVo.setSkuFirstPictureUrl(one.getSkuPictureUrl());
            }
        }
        // 订单状态描述（用于小程序端）
        setOrderStatusDesc(orderStatus, noExpressCount, orderSkuList.size(), searchOrderRespVo, null);
        return searchOrderRespVo;
    }

    private void setOrderStatusDesc(OrderStatus orderStatus, int noExpressCount, int orderSkuSize, SearchOrderRespVo searchOrderRespVo, ServerSearchOrderRespVo serverSearchOrderRespVo) {
        String orderStatusDesc = "";
        int orderStatusCode = 0;
        if (orderStatus == OrderStatus.WAIT_PAY) {
            orderStatusDesc = OrderStatusDesc.WAIT_PAY.getDescription();
            orderStatusCode = OrderStatusDesc.WAIT_PAY.getCode();
        } else if (orderStatus == OrderStatus.CANCELED) {
            orderStatusDesc = OrderStatusDesc.CLOSE.getDescription();
            orderStatusCode = OrderStatusDesc.CLOSE.getCode();
        } else if (orderStatus == OrderStatus.OVER) {
            orderStatusDesc = OrderStatusDesc.OVER.getDescription();
            orderStatusCode = OrderStatusDesc.OVER.getCode();
        } else {
            orderStatusDesc = OrderStatusDesc.REFUNDED.getDescription();
            orderStatusCode = OrderStatusDesc.REFUNDED.getCode();
        }
        if (searchOrderRespVo != null) {
            searchOrderRespVo.setStatusDesc(orderStatusDesc);
            searchOrderRespVo.setStatusCode(orderStatusCode);
        }
        if (serverSearchOrderRespVo != null) {
            serverSearchOrderRespVo.setStatusDesc(orderStatusDesc);
            serverSearchOrderRespVo.setStatusCode(orderStatusCode);
        }
    }

    private ServerSearchOrderRespVo convertServerOrderRespVoFromOrder(Order order) {

        if (order == null) {
            return null;
        }
        ServerSearchOrderRespVo searchOrderRespVo = new ServerSearchOrderRespVo();
        BeanUtils.copyProperties(order, searchOrderRespVo);

        // 支付方式名称
        searchOrderRespVo.setPayWayName(PayWayEnum.valueOf(order.getPayWay()).getDescription());
        // 订单状态名称
        searchOrderRespVo.setStatusName(OrderStatus.valueOf(order.getStatus()).getDescription());

        // 查询订单商品
        List<OrderSku> orderSkuList = orderSkuService.findSkuListByOrderNo(order.getOrderNo());

        // 订单状态名称
        OrderStatus orderStatus = OrderStatus.valueOf(order.getStatus());

        int skuTotalCount = 0;
        int noExpressCount = 0; // 待发货商品数量

        if (!CollectionUtils.isEmpty(orderSkuList)) {
            List<ServerSearchOrderSkuRespVo> searchOrderSkuRespVoList = Lists.newArrayList();
            for (OrderSku orderSku : orderSkuList) {
                ServerSearchOrderSkuRespVo searchOrderSkuRespVo = new ServerSearchOrderSkuRespVo();
                BeanUtils.copyProperties(orderSku, searchOrderSkuRespVo);
                if (orderSku.getExpressStatus() == ExpressStatus.WAIT_EXPRESS.getCode()) {
                    noExpressCount++;
                }
                // 查询订单商品信息
                SkuInfo skuInfo = skuInfoService.getById(orderSku.getSkuId());
                if (skuInfo != null) {
                    searchOrderSkuRespVo.setSkuName(skuInfo.getSkuName());
                    searchOrderSkuRespVo.setRebateDepict(skuInfo.getRebateDepict());
                    if (!StringUtils.isEmpty(skuInfo.getSkuPictureUrl())) {
                        searchOrderSkuRespVo.setSkuFirstPictureUrl(skuInfo.getSkuPictureUrl().split(",")[0]);
                    }
                    if (skuInfo.getCategoryId().equals(rebate)) {
                        searchOrderSkuRespVo.setSkuType(1);
                    }
                    if (skuInfo.getCategoryId().equals(volume)) {
                        searchOrderSkuRespVo.setSkuType(2);
                    }
                }
                if (orderSku.getPayWay() == PayWayEnum.WEIXIN_PAY.getCode()) {
                    searchOrderSkuRespVo.setTotalAmount(NumberArithmeticUtils.safeMultiply(orderSku.getSkuPrice(), orderSku.getBuyNum()));
                } else {
                    searchOrderSkuRespVo.setTotalIntegral(orderSku.getSkuIntegral() * orderSku.getBuyNum());
                }
                // 查询订单商品所选属性信息
                List<OrderSkuProperty> orderSkuPropertyList = orderSkuPropertyService.searchSkuPropertyListByOrderSkuId(orderSku.getId());
                if (!CollectionUtils.isEmpty(orderSkuPropertyList)) {
                    List<ServerSearchOrderSkuPropertyRespVo> propertyValueInfoList = orderSkuPropertyList.stream().map(orderSkuProperty -> {
                        ServerSearchOrderSkuPropertyRespVo skuPropertyValueInfo = new ServerSearchOrderSkuPropertyRespVo();
                        skuPropertyValueInfo.setPropertyValueDictId(orderSkuProperty.getPropertyValueDictId());
                        skuPropertyValueInfo.setPropertyValueName(orderSkuProperty.getPropertyValue());
                        return skuPropertyValueInfo;
                    }).collect(Collectors.toList());
                    searchOrderSkuRespVo.setPropertyValueInfoList(propertyValueInfoList);
                }
                skuTotalCount = skuTotalCount + orderSku.getBuyNum();
                searchOrderSkuRespVoList.add(searchOrderSkuRespVo);
            }
            searchOrderRespVo.setOrderSkuList(searchOrderSkuRespVoList);
            searchOrderRespVo.setSkuTotalCount(skuTotalCount);
        }
        setOrderStatusDesc(orderStatus, noExpressCount, orderSkuList.size(), null, searchOrderRespVo);
        return searchOrderRespVo;
    }
}
