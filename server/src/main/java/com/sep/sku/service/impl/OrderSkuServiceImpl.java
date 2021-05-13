package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.common.utils.JwtUtils;
import com.sep.sku.dto.OrderSkuDto;
import com.sep.sku.dto.SearchOrderSkuDto;
import com.sep.sku.enums.OrderSkuEvaluateStatus;
import com.sep.sku.enums.OrderSkuStatus;
import com.sep.sku.model.Order;
import com.sep.sku.model.OrderSku;
import com.sep.sku.repository.OrderSkuMapper;
import com.sep.sku.service.OrderSkuService;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.SearchOrderRespVo;
import com.sep.sku.vo.SearchOrderSkuRespVo;
import com.sep.sku.vo.SearchSkuRespVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 订单sku信息 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
public class OrderSkuServiceImpl extends ServiceImpl<OrderSkuMapper, OrderSku> implements OrderSkuService {

    @Autowired
    private SkuInfoService skuInfoService;

    @Override
    public List<OrderSku> findSkuListByOrderNo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return Lists.newArrayList();
        }
        QueryWrapper<OrderSku> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public IPage<OrderSkuDto> pageSearchOrderSkuResp(SearchOrderSkuDto searchOrderSkuDto) {
        int userId = (int) JwtUtils.parseJWT(searchOrderSkuDto.getToken()).get("id");
        IPage<OrderSkuDto> result = new Page<>();
        if (searchOrderSkuDto == null) {
            return result;
        }
        QueryWrapper<OrderSku> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status", OrderSkuStatus.PAYED.getCode());
        wrapper.eq("evaluate_status", OrderSkuEvaluateStatus.STAY.getCode());
        wrapper.orderByDesc("id");
        IPage<OrderSku> orderPage = baseMapper.selectPage(new Page<>(searchOrderSkuDto.getCurrentPage(), searchOrderSkuDto.getPageSize()), wrapper);
        if (orderPage != null) {
            BeanUtils.copyProperties(orderPage, result);
            if (!CollectionUtils.isEmpty(orderPage.getRecords())) {
                List<OrderSkuDto> orderSkuDtos = Lists.newArrayList();
                orderPage.getRecords().forEach(e -> {
                    OrderSkuDto orderSkuDto = new OrderSkuDto();
                    BeanUtils.copyProperties(e, orderSkuDto);
                    SearchSkuRespVo skuRespVo = skuInfoService.getSkuInfoById(orderSkuDto.getSkuId());
                    if (skuRespVo != null) {
                        orderSkuDto.setSkuRespVo(skuRespVo);
                    }
                    orderSkuDtos.add(orderSkuDto);
                });
                result.setRecords(orderSkuDtos);
            }
        }
        return result;
    }

    @Override
    public Integer updateEvaluateStatus(Integer orderSkuId) {
        OrderSku orderSku = getById(orderSkuId);
        if (orderSku == null) {
            return null;
        }
        orderSku.setEvaluateStatus(OrderSkuEvaluateStatus.OVER.getCode());
        return orderSku.updateById() ? 1 : 0;
    }
}
