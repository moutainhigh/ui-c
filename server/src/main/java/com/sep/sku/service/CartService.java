package com.sep.sku.service;

import com.sep.sku.bean.SettlementPropertyInfo;
import com.sep.sku.dto.BatchDeleteSkuCartDto;
import com.sep.sku.dto.SettlementQuickBuySkuDto;
import com.sep.sku.dto.UpdateCartDto;
import com.sep.sku.vo.CartSkuVo;

import java.util.List;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface CartService{

    /**
     * 查询购物车信息
     * @param userId
     * @return
     */
    List<CartSkuVo> searchCartList(Integer userId);

    /**
     * 添加商品至购物车
     * @param settlementQuickBuySkuDto
     * @return
     */
    List<CartSkuVo> addToCart(SettlementQuickBuySkuDto settlementQuickBuySkuDto);

    /**
     * 修改购物车商品数量
     * @param updateCartDto
     */
    void updateSkuCount(UpdateCartDto updateCartDto);

    /**
     * 批量删除购物车的商品
     * @param batchDeleteSkuCartDto
     */
    void batchDeleteSku(BatchDeleteSkuCartDto batchDeleteSkuCartDto);

    /**
     * 清空购物车
     * */
    void clean(Integer userId);

    /**
     * 生成订单商品唯一标识（skuId+sku属性）生成
     * @param skuId
     * @param propertyInfoList
     * @return
     */
    String generatorSkuUniqueKey(int skuId, List<SettlementPropertyInfo> propertyInfoList);



}
