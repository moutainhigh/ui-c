package com.sep.sku.service;

import com.sep.sku.bean.OrderSkuValuationInfo;
import com.sep.sku.dto.CouponValuationDto;
import com.sep.sku.dto.SettlementSkuDto;
import com.sep.sku.dto.ZdwCouponDto;
import com.sep.sku.vo.CouponValuationRespVo;
import com.sep.sku.vo.SearchSkuRespVo;
import com.sep.sku.vo.SettlementIndexRespVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 结算 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface SettlementService{

    /**
     * 结算页展示
     * @param userId 用户id
     * @param settlementSkuDtoList 商品信息
     * @return
     */
    SettlementIndexRespVo skuSettlement(int userId, List<SettlementSkuDto> settlementSkuDtoList);

    /**
     * 商品购买校验
     * @param skuId
     * @param buyNum
     * @return
     */
    SearchSkuRespVo validateSkuForBuy(Integer skuId, int buyNum);

    /**
     * 优惠券计价
     * @param couponValuationDto
     * @return
     */
//    CouponValuationRespVo couponValuation(CouponValuationDto couponValuationDto);

    /**
     * 根据商品计算价钱
     * @param orderSkuValuationInfoList
     * @return
     */
    BigDecimal computeOrderTotalAmount(List<OrderSkuValuationInfo> orderSkuValuationInfoList);


//    ZdwCouponDto getZdwCouponDto(String  encryptParam) throws Exception ;

    BigDecimal getSkuPrice(ZdwCouponDto zdwCouponDto, BigDecimal skuCurrentPrice);

//    String getZdwRemark(ZdwCouponDto dto);
//


}
