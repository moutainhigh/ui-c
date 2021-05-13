package com.sep.sku.service.impl;

import com.sep.sku.bean.OrderSkuValuationInfo;
import com.sep.sku.dto.SettlementSkuDto;
import com.sep.sku.service.SettlementService;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.service.ValuationService;
import com.sep.sku.vo.CartValuationRespVo;
import com.sep.sku.vo.SearchSkuRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 结算服务实现
 *
 * @author zhangkai
 * @date 2020年01月07日 23:59
 */
@Service
@Slf4j
public class ValuationServiceImpl implements ValuationService{

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SettlementService settlementService;

    @Override
    public CartValuationRespVo cartValuation(List<SettlementSkuDto> settlementSkuDtoList) {
        CartValuationRespVo cartValuationRespVo = new CartValuationRespVo();
        // 商品计价
        List<OrderSkuValuationInfo> skuValuationInfoList = settlementSkuDtoList.stream().map(settlementSkuInfo -> {
            OrderSkuValuationInfo orderSkuValuationInfo = new OrderSkuValuationInfo();
            BeanUtils.copyProperties(settlementSkuInfo,orderSkuValuationInfo);
            SearchSkuRespVo skuRespVo = skuInfoService.getSkuInfoById(settlementSkuInfo.getSkuId());
            orderSkuValuationInfo.setCurrentPrice(skuRespVo.getCurrentPrice());
            return orderSkuValuationInfo;
        }).collect(Collectors.toList());

        cartValuationRespVo.setTotalSkuAmount(settlementService.computeOrderTotalAmount(skuValuationInfoList));
        return cartValuationRespVo;
    }
}
