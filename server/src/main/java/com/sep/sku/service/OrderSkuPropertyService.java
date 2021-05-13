package com.sep.sku.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.sku.model.OrderSkuProperty;

import java.util.List;

/**
 * <p>
 * 订单sku属性信息 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface OrderSkuPropertyService extends IService<OrderSkuProperty> {

    List<OrderSkuProperty> searchSkuPropertyListByOrderSkuId(Integer orderSkuId);

}
