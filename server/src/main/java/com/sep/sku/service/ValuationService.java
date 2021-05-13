package com.sep.sku.service;

import com.sep.sku.dto.SettlementSkuDto;
import com.sep.sku.vo.CartValuationRespVo;

import java.util.List;

/**
 * <p>
 * 计价 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface ValuationService {

    /**
     * 购物车内商品计价
     * @param settlementSkuDtoList
     * @return
     */
    CartValuationRespVo cartValuation(List<SettlementSkuDto> settlementSkuDtoList);

}
