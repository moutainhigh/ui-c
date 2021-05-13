package com.sep.sku.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.sku.bean.StatisticalMonthOrderCount;
import com.sep.sku.dto.CounselorStatisticalOrderDto;
import com.sep.sku.dto.ServerBatchSearchOrderDto;
import com.sep.sku.dto.StatisticalOrderDto;
import com.sep.sku.vo.CounselorStatisticalOrderRespVo;
import com.sep.sku.vo.HomeOrderStatisticalRespVo;
import com.sep.sku.vo.ServerSearchOrderRespVo;
import com.sep.sku.vo.StatisticalOrderRespVo;
import com.sep.sku.dto.*;
import com.sep.sku.model.Order;
import com.sep.sku.vo.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * sku订单表 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface OrderService extends IService<Order> {

    /**
     * 提交订单
     * @param commitOrderDto
     */
    CommitOrderRespVo commitOrder(CommitOrderDto commitOrderDto);

    /**
     * 处理微信异步成功通知
     */
    void wxNotifyDeal(WxNotifyDealDto wxNotifyDealDto);

    /**
     * 小程序端分页查询订单商品信息
     * @param searchSkuOrderDto
     * @return
     */
    IPage<SearchOrderRespVo> pageSearchSkuOrderInfo(SearchSkuOrderDto searchSkuOrderDto);

    /**
     * 根据订单号查询订单详情
     * @param orderNo
     * @return
     */
    Order getSkuOrderInfoByOrderNo(String orderNo);

    /**
     * 后台分页查询订单商品信息
     * @param backSearchOrderDto
     * @return
     */
    IPage<SearchOrderRespVo> backPageSearchSkuOrderInfo(BackSearchOrderDto backSearchOrderDto);

    /**
     * 服务端分页查询订单商品信息
     * @param serverBackSearchOrderDto
     * @return
     */
    Page<ServerSearchOrderRespVo> serverPageSearchSkuOrderInfo(ServerBatchSearchOrderDto serverBackSearchOrderDto);


    /**
     * 修改订单商品状态
     * @param updateOrderStatusDto
     * @return
     */
    int updateOrderStatus(UpdateOrderStatusDto updateOrderStatusDto);

    /**
     * 修改订单商品状态
     * @param updateOrderSkuStatusDto
     * @return
     */
    boolean updateOrderSkuStatus(UpdateOrderSkuStatusDto updateOrderSkuStatusDto);

    /**
     * 去支付
     * @param gotoPayDto
     * @return
     */
    CommitOrderRespVo gotoPay(GotoPayDto gotoPayDto);

    /**
     * 查询订单详情
     * @param searchOrderDetailDto
     */
    SearchOrderRespVo searchOrderDetail(SearchOrderDetailDto searchOrderDetailDto);

    /**
     * 统计用户订单信息
     * @param statisticalOrderDto
     * @return
     */
    StatisticalOrderRespVo statisticalOrder(StatisticalOrderDto statisticalOrderDto);


    /**
     * 统计销售代表订单信息
     * @param statisticalOrderDto
     * @return
     */
    CounselorStatisticalOrderRespVo counselorStatisticalOrder(CounselorStatisticalOrderDto statisticalOrderDto);

    /**
     * 消费记录查询
     * @param consumeRecordDto
     * @return
     */
    ConsumeRecordRespVo consumeRecord(ConsumeRecordDto consumeRecordDto);

    /**
     * 后台查询订单信息
     * @param id
     * @return
     */
    BackSearchOrderRespVo getSkuOrderInfoById(Integer id);

    /**
     * 批量修改订单商品发货状态
     * @param updateOrderExpressStatusDto
     * @return
     */
    boolean updateExpressStatus(UpdateOrderExpressStatusDto updateOrderExpressStatusDto);

    /**
     * 修改自提状态
     * @param updateOrderFetchStatusDto
     * @return
     */
    /*boolean updateFetchStatus(UpdateOrderFetchStatusDto updateOrderFetchStatusDto);*/


    /**
     * 统计已支付的订单总量
     * @return
     */
    int statisticalOrderCount();

    /**
     * 统计已支付的订单总金额
     * @return
     */
    BigDecimal statisticalOrderAmount();

    /**
     * 按天统计订单量
     * @param startDate
     * @param endDate
     * @return
     */
    List<StatisticalMonthOrderCount> statisticalMonthOrderCount(String startDate, String endDate);

    /**
     * 后台首页订单和商品统计
     * @param date
     * @return
     */
    HomeOrderStatisticalRespVo homeOrderAndSkuStatistical(String date);

}
