package com.sep.sku.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.sku.dto.OrderSkuDto;
import com.sep.sku.dto.SearchOrderSkuDto;
import com.sep.sku.dto.SearchSkuOrderDto;
import com.sep.sku.model.OrderSku;
import com.sep.sku.vo.SearchOrderRespVo;
import com.sep.sku.vo.SearchOrderSkuRespVo;

import java.util.List;

/**
 * <p>
 * 订单sku信息 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface OrderSkuService extends IService<OrderSku> {

    List<OrderSku> findSkuListByOrderNo(String orderNo);

    IPage<OrderSkuDto> pageSearchOrderSkuResp(SearchOrderSkuDto searchOrderSkuDto);

    Integer updateEvaluateStatus(Integer orderSkuId);


}
