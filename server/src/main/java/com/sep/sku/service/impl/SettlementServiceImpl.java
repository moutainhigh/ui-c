package com.sep.sku.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sep.common.utils.AesEncryptUtils;
import com.sep.coupon.service.CouponService;
import com.sep.coupon.vo.CouponDetailsOutPut;
import com.sep.point.service.PointService;
import com.sep.sku.bean.CouponValuationRespSkuInfo;
import com.sep.sku.bean.OrderSkuValuationInfo;
import com.sep.sku.bean.SettlementSkuInfo;
import com.sep.sku.dto.ZdwCouponDto;
import com.sep.sku.enums.SkuStatus;
import com.sep.sku.dto.CouponValuationDto;
import com.sep.sku.dto.SettlementSkuDto;
import com.sep.sku.service.SettlementService;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.CouponValuationRespVo;
import com.sep.sku.vo.SearchSkuRespVo;
import com.sep.sku.vo.SettlementIndexRespVo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.NumberArithmeticUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 结算服务实现
 *
 * @author zhangkai
 * @date 2020年01月07日 23:59
 */
@Service
@Slf4j
public class SettlementServiceImpl implements SettlementService {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private PointService pointService;

    @Override
    public SettlementIndexRespVo skuSettlement(int userId, List<SettlementSkuDto> settlementSkuDtoList) {
        log.info("【settlementOrder】->settlementSkuListDto : {}", JSON.toJSONString(settlementSkuDtoList));
        SettlementIndexRespVo settlementIndexRespVo = new SettlementIndexRespVo();

        List<SearchSkuRespVo> skuRespVoList = Lists.newArrayList();

        // 校验商品有效性及库存量
        for (SettlementSkuDto settlementSkuInfo : settlementSkuDtoList) {
            try {
                SearchSkuRespVo skuRespVo = validateSkuForBuy(settlementSkuInfo.getSkuId(), settlementSkuInfo.getBuyNum());
                skuRespVoList.add(skuRespVo);
            } catch (SepCustomException sepException) {
                throw sepException;
            } catch (Exception exception) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "进入结算页失败");
            }
        }

        log.info("【settlementOrder】->skuRespVo : {}", JSON.toJSONString(skuRespVoList));
        Map<Integer, SearchSkuRespVo> searchSkuRespVoMap = new HashMap<>();
        for (SearchSkuRespVo searchSkuRespVo : skuRespVoList) {
            if (searchSkuRespVoMap.get(searchSkuRespVo.getId()) == null) {
                searchSkuRespVoMap.put(searchSkuRespVo.getId(), searchSkuRespVo);
            }
        }

        // 结算页展示商品信息
        List<SettlementSkuInfo> skuRespInfoList = settlementSkuDtoList.stream().map(settlementSkuDto -> {
            SettlementSkuInfo settlementSkuInfo = new SettlementSkuInfo();
            SearchSkuRespVo searchSkuRespVo = searchSkuRespVoMap.get(settlementSkuDto.getSkuId());
            BeanUtils.copyProperties(settlementSkuDto, settlementSkuInfo);
            BeanUtils.copyProperties(searchSkuRespVo, settlementSkuInfo);
            return settlementSkuInfo;
        }).collect(Collectors.toList());

        // 商品计价
        List<OrderSkuValuationInfo> skuValuationInfoList = skuRespInfoList.stream().map(settlementSkuInfo -> {
            OrderSkuValuationInfo orderSkuValuationInfo = new OrderSkuValuationInfo();
            BeanUtils.copyProperties(settlementSkuInfo, orderSkuValuationInfo);
            return orderSkuValuationInfo;
        }).collect(Collectors.toList());

        // 订单总金额
        settlementIndexRespVo.setTotalAmount(computeOrderTotalAmount(skuValuationInfoList));
        // 商品信息
        settlementIndexRespVo.setBuySkuList(skuRespInfoList);
//        // 订单总积分
//        settlementIndexRespVo.setTotalIntegral(computeOrderTotalIntegral(skuRespInfoList));
//
//        // 是否支持现金支付
//        settlementIndexRespVo.setIsCashPay(isCashPay(skuRespInfoList));

        // 用户积分余额
//        int userRemainIntegral = 0;
//        try {
//            userRemainIntegral = pointService.current(userId);
//        } catch (Exception e) {
//            log.error("【skuSettlement】,search user point fail");
//            e.printStackTrace();
//        }
//        settlementIndexRespVo.setUserRemainIntegral(userRemainIntegral);

        // 是否支持积分兑换
//        settlementIndexRespVo.setIsIntegralExchange(isIntegralExchange(skuRespInfoList)
//                && settlementIndexRespVo.getUserRemainIntegral() >= settlementIndexRespVo.getTotalIntegral());

        return settlementIndexRespVo;
    }

//    public String getZdwRemark(ZdwCouponDto dto) {
//        StringBuffer remark = new StringBuffer();
//        remark.append("优惠劵");
//        remark.append(dto.getCurrentPrice());
//        remark.append("元");
//        if (dto.getSkuType().equals(1)) {
//            remark.append(dto.getRebate());
//            remark.append("折劵");
//        } else if (dto.getSkuType().equals(2)) {
//            remark.append("抵");
//            remark.append(dto.getReplaceCash());
//            remark.append("元劵");
//        }
//        return remark.toString();
//    }

    @Override
    public SearchSkuRespVo validateSkuForBuy(Integer skuId, int buyNum) {
        if (skuId == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品ID不能为空");
        }
        SearchSkuRespVo skuRespVo = skuInfoService.getSkuInfoById(skuId);
        if (skuRespVo == null) {
            throw new SepCustomException(ResponseData.SKU_NO_EXIST, "商品有误");
        }
        if (skuRespVo.getStatus() != SkuStatus.ON_PUTAWAY.getCode()) {
            log.error("【validateSkuForBuy】-> skuId: {},sku_status : {}", skuId, skuRespVo.getStatus());
            throw new SepCustomException(ResponseData.SKU_STATUS_ERROR, "商品未上架或已售磬");
        }
        // todo 考虑并发库存超卖情况
        if (skuRespVo.getStockNum() > 0 && (skuRespVo.getSaleNum() + buyNum) > skuRespVo.getStockNum()) {
            log.error("【validateSkuForBuy】-> sku_stock not enough,bun_num:{},stock_num:{}", buyNum, skuRespVo.getStockNum());
            throw new SepCustomException(ResponseData.SKU_STATUS_ERROR, "商品库存不足");
        }
        return skuRespVo;
    }

//    @Override
//    public CouponValuationRespVo couponValuation(CouponValuationDto couponValuationDto) {
//        if (couponValuationDto.getCouponId() == null) {
//            throw new SepCustomException(ResponseData.SKU_STATUS_ERROR, "优惠券id为空");
//        }
////        CouponDetailsOutPut couponDetailsOutPut = couponService.details(couponValuationDto.getCouponId());
////        if (couponDetailsOutPut == null) {
////            throw new SepCustomException(ResponseData.SKU_STATUS_ERROR, "优惠券不存在");
////        }
//        // 商品计价
//        List<OrderSkuValuationInfo> skuValuationInfoList = couponValuationDto.getBuySkuList().stream().map(buySku -> {
//            OrderSkuValuationInfo orderSkuValuationInfo = new OrderSkuValuationInfo();
//            BeanUtils.copyProperties(buySku, orderSkuValuationInfo);
//            SearchSkuRespVo skuRespVo = skuInfoService.getSkuInfoById(buySku.getSkuId());
//            orderSkuValuationInfo.setCurrentPrice(skuRespVo.getCurrentPrice());
//            return orderSkuValuationInfo;
//        }).collect(Collectors.toList());
//
//        // 订单总金额
//        BigDecimal totalAmount = computeOrderTotalAmount(skuValuationInfoList);
//        // 优惠券使用金额条件
////        BigDecimal couponMonetary = couponDetailsOutPut.getMonetary();
////        // 优惠券直减金额
////        BigDecimal lapseAmount = couponDetailsOutPut.getCashRebate();
////        if (couponMonetary != null && totalAmount.compareTo(couponMonetary) < 0) {
////            log.error("【couponValuation】，not meet coupon use conditions,currentTotalMoeny:{},couponAmount:{}", totalAmount, couponMonetary);
////            throw new SepCustomException(ResponseData.SKU_STATUS_ERROR, "不满足优惠券使用条件");
////        }
//        // 优惠券计价
//        return convertSkuCouponPrice(totalAmount, lapseAmount, skuValuationInfoList);
//
//    }

    /**
     * 计算总金额
     *
     * @param orderSkuValuationInfoList
     * @return
     */
    @Override
    public BigDecimal computeOrderTotalAmount(List<OrderSkuValuationInfo> orderSkuValuationInfoList) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (CollectionUtils.isEmpty(orderSkuValuationInfoList)) {
            return totalAmount;
        }

        for (OrderSkuValuationInfo orderSkuValuationInfo : orderSkuValuationInfoList) {
//            if (StringUtils.isNotBlank(orderSkuValuationInfo.getEncryptParam()) && orderSkuValuationInfoList.size() == 1) {
//                ZdwCouponDto zdwCouponDto = new ZdwCouponDto();
//                try {
//                    zdwCouponDto = getZdwCouponDto(orderSkuValuationInfo.getEncryptParam());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (zdwCouponDto != null && zdwCouponDto.getFacilitatoSkuId().equals(orderSkuValuationInfo.getSkuId())) {
//                    if (orderSkuValuationInfo.getBuyNum() > 1) {
//                        BigDecimal skuPrice = BigDecimal.ZERO;
//                        BigDecimal currentSkuTotalAmount = NumberArithmeticUtils.safeMultiply(orderSkuValuationInfo.getCurrentPrice(), orderSkuValuationInfo.getBuyNum() - 1);
//                        totalAmount = NumberArithmeticUtils.safeAdd(getSkuPrice(zdwCouponDto, orderSkuValuationInfo.getCurrentPrice()), currentSkuTotalAmount);
//
//                    } else {
//                        totalAmount = getSkuPrice(zdwCouponDto, orderSkuValuationInfo.getCurrentPrice());
//                    }
//                }
//            } else {
                BigDecimal skuCurrentPrice = BigDecimal.ZERO;
                if (orderSkuValuationInfo.getCurrentPrice() != null) {
                    skuCurrentPrice = orderSkuValuationInfo.getCurrentPrice();
                }
                BigDecimal currentSkuTotalAmount = NumberArithmeticUtils.safeMultiply(skuCurrentPrice, orderSkuValuationInfo.getBuyNum());
                totalAmount = NumberArithmeticUtils.safeAdd(totalAmount, currentSkuTotalAmount);
//            }

        }
        return totalAmount;
    }

    public BigDecimal getSkuPrice(ZdwCouponDto zdwCouponDto, BigDecimal skuCurrentPrice) {
        BigDecimal skuPrice = BigDecimal.ZERO;
        if (zdwCouponDto.getSkuType().equals(1)) {
            BigDecimal rebate = new BigDecimal("0." + zdwCouponDto.getRebate());
            skuPrice = NumberArithmeticUtils.safeMultiply(skuCurrentPrice, rebate);

        } else if (zdwCouponDto.getSkuType().equals(2)) {
            skuPrice = NumberArithmeticUtils.safeSubtract(skuCurrentPrice, zdwCouponDto.getReplaceCash());
        } else {
            skuPrice = skuCurrentPrice;
        }
        return skuPrice;
    }

//    @Override
//    public ZdwCouponDto getZdwCouponDto(String encryptParam) throws Exception {
//        if (StringUtils.isNotBlank(encryptParam)) {
//            ZdwCouponDto zdwCouponDto = new ZdwCouponDto();
//            String jsonParam = AesEncryptUtils.aesDecrypt(encryptParam, "d7b86f6a234abcda");
//            JSONObject jsonObject = JSONObject.parseObject(jsonParam);
//            zdwCouponDto.setFacilitatoSkuId(jsonObject.getInteger("facilitatoSkuId"));
//            zdwCouponDto.setOrderSkuId(jsonObject.getInteger("orderSkuId"));
//            zdwCouponDto.setRebate(jsonObject.getInteger("rebate"));
//            zdwCouponDto.setReplaceCash(jsonObject.getBigDecimal("replaceCash"));
//            zdwCouponDto.setSkuType(jsonObject.getInteger("skuType"));
//            zdwCouponDto.setCurrentPrice(jsonObject.getBigDecimal("currentPrice"));
//
//            return zdwCouponDto;
//        }
//        return null;
//    }

    /**
     * 优惠券计价
     *
     * @param totalAmount
     * @param lapseAmount
     * @param skuValuationInfoList
     * @return
     */
    private CouponValuationRespVo convertSkuCouponPrice(BigDecimal totalAmount, BigDecimal lapseAmount, List<OrderSkuValuationInfo> skuValuationInfoList) {

        CouponValuationRespVo couponValuationRespVo = new CouponValuationRespVo();

        // 优惠券计算
        List<CouponValuationRespSkuInfo> couponValuationOriginPriceSkuInfoList = skuValuationInfoList.stream().map(skuValuationInfo -> {
            CouponValuationRespSkuInfo couponValuationRespSkuInfo = new CouponValuationRespSkuInfo();
            BeanUtils.copyProperties(skuValuationInfo, couponValuationRespSkuInfo);
            couponValuationRespSkuInfo.setCouponPrice(skuValuationInfo.getCurrentPrice());
            return couponValuationRespSkuInfo;
        }).collect(Collectors.toList());

        // 直减金额为空 or 直减金额为0元，优惠券金额为原价
        if (lapseAmount == null || lapseAmount.compareTo(BigDecimal.ZERO) <= 0) {
            couponValuationRespVo.setBuySkuList(couponValuationOriginPriceSkuInfoList);
            couponValuationRespVo.setTotalAmount(totalAmount);
            return couponValuationRespVo;
        }

        // 如果产品总金额小于等于优惠券直减金额，优惠券金额为0
        if (totalAmount.compareTo(lapseAmount) <= 0) {
            List<CouponValuationRespSkuInfo> couponValuationZeroPriceList = skuValuationInfoList.stream().map(skuValuationInfo -> {
                CouponValuationRespSkuInfo couponValuationRespSkuInfo = new CouponValuationRespSkuInfo();
                BeanUtils.copyProperties(skuValuationInfo, couponValuationRespSkuInfo);
                couponValuationRespSkuInfo.setCouponPrice(BigDecimal.ZERO);
                return couponValuationRespSkuInfo;
            }).collect(Collectors.toList());
            couponValuationRespVo.setBuySkuList(couponValuationZeroPriceList);
            couponValuationRespVo.setTotalAmount(BigDecimal.ZERO);
            return couponValuationRespVo;
        }

        // 根据权重分摊打折金额
        Collections.sort(skuValuationInfoList, new Comparator<OrderSkuValuationInfo>() {
            @Override
            public int compare(OrderSkuValuationInfo o1, OrderSkuValuationInfo o2) {
                return o1.getCurrentPrice().compareTo(o2.getCurrentPrice());
            }
        });

        for (int index = 0; index < couponValuationOriginPriceSkuInfoList.size(); index++) {
            CouponValuationRespSkuInfo couponSkuRespVo = couponValuationOriginPriceSkuInfoList.get(index);
            BigDecimal currentSkuTotalAmount = couponSkuRespVo.getCurrentPrice().multiply(BigDecimal.valueOf(couponSkuRespVo.getBuyNum()));
            BigDecimal currentSkuCouponTotalAmount;
            if (index == skuValuationInfoList.size() - 1) {
                currentSkuCouponTotalAmount = currentSkuTotalAmount.subtract(lapseAmount).add(totalAmount)
                        .subtract(getValuationingTotalAmount(couponValuationOriginPriceSkuInfoList)).setScale(2, RoundingMode.HALF_UP);
            } else {
                currentSkuCouponTotalAmount = currentSkuTotalAmount
                        .subtract(currentSkuTotalAmount.multiply(lapseAmount).divide(totalAmount, 4, RoundingMode.HALF_UP))
                        .setScale(2, RoundingMode.HALF_UP);
            }
            couponSkuRespVo.setCouponPrice(currentSkuCouponTotalAmount.divide(
                    BigDecimal.valueOf(couponSkuRespVo.getBuyNum()), 2, RoundingMode.HALF_UP));
        }
        couponValuationRespVo.setTotalAmount(totalAmount.subtract(lapseAmount));
        couponValuationRespVo.setBuySkuList(couponValuationOriginPriceSkuInfoList);
        return couponValuationRespVo;
    }

    private BigDecimal getValuationingTotalAmount(List<CouponValuationRespSkuInfo> skuPriceInfoList) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CouponValuationRespSkuInfo skuPriceInfo : skuPriceInfoList) {
            totalAmount = totalAmount.add(skuPriceInfo.getCouponPrice().multiply(BigDecimal.valueOf(skuPriceInfo.getBuyNum())));
        }
        return totalAmount;
    }

    /**
     * 是否支持积分兑换
     *
     * @param skuRespInfoList
     * @return
     */
//    private boolean isIntegralExchange(List<SettlementSkuInfo> skuRespInfoList) {
//        for (SettlementSkuInfo settlementSkuInfo : skuRespInfoList) {
//            if (!settlementSkuInfo.getIsIntegralExchange()) {
//                return false;
//            }
//        }
//        return true;
//    }

    /**
     * 是否支持现金支付
     *
     * @param skuRespInfoList
     * @return
     */
//    private boolean isCashPay(List<SettlementSkuInfo> skuRespInfoList) {
//        for (SettlementSkuInfo settlementSkuInfo : skuRespInfoList) {
//            if (!settlementSkuInfo.getIsCashPay()) {
//                return false;
//            }
//        }
//        return true;
//    }

    /**
     * 计算订单总积分
     *
     * @param skuRespInfoList
     * @return
     */
//    private int computeOrderTotalIntegral(List<SettlementSkuInfo> skuRespInfoList) {
//        int totalIntegral = 0;
//        if (CollectionUtils.isEmpty(skuRespInfoList)) {
//            return totalIntegral;
//        }
//        for (SettlementSkuInfo settlementSkuInfo : skuRespInfoList) {
//            int currentSkuTotalIntegral = 0;
//            if (settlementSkuInfo.getIsIntegralExchange()) {
//                currentSkuTotalIntegral = settlementSkuInfo.getIntegralNum() * settlementSkuInfo.getBuyNum();
//            }
//            totalIntegral = totalIntegral + currentSkuTotalIntegral;
//        }
//        return totalIntegral;
//    }
}
