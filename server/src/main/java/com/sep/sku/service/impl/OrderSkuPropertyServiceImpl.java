package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.sku.model.OrderSkuProperty;
import com.sep.sku.repository.OrderSkuPropertyMapper;
import com.sep.sku.service.OrderSkuPropertyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单sku属性信息 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
public class OrderSkuPropertyServiceImpl extends ServiceImpl<OrderSkuPropertyMapper, OrderSkuProperty> implements OrderSkuPropertyService {

    @Override
    public List<OrderSkuProperty> searchSkuPropertyListByOrderSkuId(Integer orderSkuId) {
        if(orderSkuId == null){
            return Lists.newArrayList();
        }
        QueryWrapper<OrderSkuProperty> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sku_id",orderSkuId);
        return baseMapper.selectList(wrapper);
    }
}
