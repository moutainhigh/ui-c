package com.sep.sku.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.sku.model.OrderPayLog;

import java.util.List;

/**
 * <p>
 * sku订单交易日志表 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface OrderPayLogService extends IService<OrderPayLog> {

    List<OrderPayLog> getPayLogListByOrderNo(String orderNo);

}
