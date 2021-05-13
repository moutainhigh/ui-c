package com.sep.sku.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.sku.model.SkuProperty;

import java.util.List;

/**
 * <p>
 * sku属性规则表 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface SkuPropertyService extends IService<SkuProperty> {

    /**
     * 根据商品id查询商品属性集合
     * @param skuId
     * @return
     */
    List<SkuProperty> findSkuPropertyListBySkuId(Integer skuId);



}
