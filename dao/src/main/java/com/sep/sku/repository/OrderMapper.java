package com.sep.sku.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sep.sku.bean.*;
import com.sep.sku.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * sku订单表 Mapper 接口
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Component
public interface OrderMapper extends BaseMapper<Order> {

    List<StatisticalOrderInfo> statisticalOrderAmountAndNum(@Param("list") List<Integer> userIdList, @Param("payway") Integer payway);

    List<CounselorStatisticalOrderInfo> statisticalCounselorOrderAmountAndNum(@Param("list") List<Integer> counselorIdList, @Param("payway") Integer payway);

    ConsumeRecordInfo statisticalOrderAmountAndIntegral(ConsumeRecordQueryInfo consumeRecordDto);

    int statisticalOrderCount();

    BigDecimal statisticalOrderAmount();

    List<StatisticalMonthOrderCount> statisticalMonthOrderCount(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
