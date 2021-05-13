package com.sep.sku.service;

import com.sep.sku.dto.OrderInformationDto;
import com.sep.sku.model.OrderInformation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.sku.model.OrderSku;
import com.sep.sku.vo.BackSearchOrderRespVo;
import com.sep.sku.vo.OrderInformationVo;

import java.util.List;

/**
 * <p>
 * 关联手机号码  服务类
 * </p>
 *
 * @author liutianao
 * @since 2020-09-17
 */
public interface OrderInformationService extends IService<OrderInformation> {

    void add(List<String> phones, String orderNo,Integer skuid);

    List<OrderInformation> findSkuListByOrderNo(String orderNo);

    OrderInformationVo getSkuOrderInfoById(Integer id);

    Integer updateState(OrderInformationDto orderInformationDto);
}
