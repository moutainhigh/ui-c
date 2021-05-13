package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.sku.model.OrderPayLog;
import com.sep.sku.repository.OrderPayLogMapper;
import com.sep.sku.service.OrderPayLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * sku订单交易日志表 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
public class OrderPayLogServiceImpl extends ServiceImpl<OrderPayLogMapper, OrderPayLog> implements OrderPayLogService {

    @Override
    public List<OrderPayLog> getPayLogListByOrderNo(String orderNo) {
        if(StringUtils.isEmpty(orderNo)){
            return Lists.newArrayList();
        }
        QueryWrapper<OrderPayLog> orderPayLogQueryWrapper = new QueryWrapper<>();
        orderPayLogQueryWrapper.eq("order_no",orderNo);
        orderPayLogQueryWrapper.orderByDesc("id");
        return baseMapper.selectList(orderPayLogQueryWrapper);
    }
}
