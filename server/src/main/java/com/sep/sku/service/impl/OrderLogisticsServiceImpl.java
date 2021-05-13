package com.sep.sku.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.sku.dto.SearchOrderDetailDto;
import com.sep.sku.enums.ExpressCompanyEnum;
import com.sep.sku.model.OrderLogistics;
import com.sep.sku.repository.OrderLogisticsMapper;
import com.sep.sku.service.OrderLogisticsService;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.vo.SearchOrderLogisticsRespVo;
import com.sep.sku.vo.SearchSkuRespVo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单物流信息 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-06
 */
@Service
public class OrderLogisticsServiceImpl extends ServiceImpl<OrderLogisticsMapper, OrderLogistics> implements OrderLogisticsService {

    @Autowired
    private SkuInfoService skuInfoService;

//    @Override
//    public List<SearchOrderLogisticsRespVo> searchOrderLogisticsList(SearchOrderDetailDto searchOrderDetailDto) {
//        if(StringUtils.isEmpty(searchOrderDetailDto.getOrderNo())){
//            throw new SepCustomException(ResponseData.STATUS_CODE_400,"订单号为空");
//        }
//
//        List<SearchOrderLogisticsRespVo> logisticsRespVoList = Lists.newArrayList();
//        QueryWrapper<OrderLogistics> wrapper = new QueryWrapper<>();
//        wrapper.eq("order_no",searchOrderDetailDto.getOrderNo());
//
//        List<OrderLogistics> logisticsList = baseMapper.selectList(wrapper);
//        if(CollectionUtils.isEmpty(logisticsList)){
//            return logisticsRespVoList;
//        }
//
//        Map<String, List<OrderLogistics>> orderLogisticsMap = logisticsList.stream().collect(Collectors.groupingBy(OrderLogistics::getExpressNo));
//
//        for(String expressNo : orderLogisticsMap.keySet()){
//            SearchOrderLogisticsRespVo logisticsRespVo = new SearchOrderLogisticsRespVo();
//            logisticsRespVo.setExpressNo(expressNo);
//            List<OrderLogistics> orderLogisticsList = orderLogisticsMap.get(expressNo);
//            if(!CollectionUtils.isEmpty(orderLogisticsList)){
//                // 查询物流信息
//                ExpressCompanyEnum eceEnum = ExpressCompanyEnum.valueOf(orderLogisticsList.get(0).getExpressType());
//                if(eceEnum != null){
//                    logisticsRespVo.setExpressName(eceEnum.getDescription());
//                }
//                List<SearchSkuRespVo> skuInfoList = orderLogisticsList.stream().map(orderLogistics -> {
//                    SearchSkuRespVo settlementSkuInfo = skuInfoService.getSkuInfoById(orderLogistics.getSkuId());
//                    return settlementSkuInfo;
//                }).collect(Collectors.toList());
//
//                logisticsRespVo.setSkuInfoList(skuInfoList);
//                logisticsRespVo.setCreateTime(orderLogisticsList.get(0).getCreateTime());
//            }
//            logisticsRespVoList.add(logisticsRespVo);
//        }
//        return logisticsRespVoList;
//    }
}
