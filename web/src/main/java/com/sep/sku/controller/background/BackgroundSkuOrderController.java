package com.sep.sku.controller.background;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.sku.bean.ExportOrderInfo;
import com.sep.sku.enums.PayWayEnum;
import com.sep.sku.dto.*;
import com.sep.sku.model.OrderSku;
import com.sep.sku.model.SkuInfo;
import com.sep.sku.service.OrderInformationService;
import com.sep.sku.service.OrderService;
import com.sep.sku.service.OrderSkuService;
import com.sep.sku.service.SkuInfoService;
import com.sep.sku.view.SkuOrderView;
import com.sep.sku.vo.BackSearchOrderRespVo;
import com.sep.sku.vo.OrderInformationVo;
import com.sep.sku.vo.SearchOrderRespVo;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 后台商品订单相关
 *
 * @author zhangkai
 * @date 2019年08月10日 18:51
 */
@RequestMapping("background/sku_order")
@RestController
@Api(value = "后台商品订单相关API", tags = {"后台商品订单相关API"})
public class BackgroundSkuOrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderSkuService orderSkuService;

    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private OrderInformationService orderInformationService;

    @PostMapping(value = "/pageSearchSkuOrderInfo")
    @ApiOperation(value = "分页查询商品订单信息", httpMethod = "POST")
    public ResponseData<IPage<SearchOrderRespVo>> pageSearchCardOrderInfo(@RequestBody BackSearchOrderDto backSearchOrderDto){
        IPage<SearchOrderRespVo> orderRespVoPage = orderService.backPageSearchSkuOrderInfo(backSearchOrderDto);
        return ResponseData.OK(orderRespVoPage);
    }

    @PostMapping(value= "/getCardOrderInfoById")
    @ApiOperation(value = "查询商品订单详情",httpMethod = "POST")
    public ResponseData<OrderInformationVo> getCardOrderInfoById(@RequestBody IdDto idDto){
        if(idDto == null || idDto.getId() == null){
            return ResponseData.ERROR("id为空");
        }
        OrderInformationVo searchOrderRespVo = orderInformationService.getSkuOrderInfoById(idDto.getId());
        return ResponseData.OK(searchOrderRespVo);
    }

//    @PostMapping(value = "/sendOutOrder")
//    @ApiOperation(value = "批量修改订单商品发货状态",httpMethod = "POST")
//    public ResponseData sendOutOrder(@RequestBody UpdateOrderExpressStatusDto updateOrderExpressStatusDto){
//        boolean result = orderService.updateExpressStatus(updateOrderExpressStatusDto);
//        return result ? ResponseData.OK() : ResponseData.ERROR("订单发货失败!");
//    }

    @PostMapping(value = "/export")
    @ApiOperation(value = "订单导出", httpMethod = "POST")
    public ModelAndView orderExport(@RequestBody BackSearchOrderDto backSearchOrderDto) {
        backSearchOrderDto.setPageSize(Long.MAX_VALUE);
        IPage<SearchOrderRespVo> orderRespVoPage = orderService.backPageSearchSkuOrderInfo(backSearchOrderDto);
        List<ExportOrderInfo> exportOrderInfoList = orderRespVoPage.getRecords().stream().map(orderInfo ->{
            ExportOrderInfo exportOrderInfo = new ExportOrderInfo();
            BeanUtils.copyProperties(orderInfo,exportOrderInfo);
            exportOrderInfo.setOrderAmount(orderInfo.getPayWay() == PayWayEnum.WEIXIN_PAY.getCode() ? orderInfo.getAmount().toString() : String.valueOf(orderInfo.getIntegralNum()));
            exportOrderInfo.setOrderStatus(orderInfo.getStatusDesc());
            exportOrderInfo.setPayway(orderInfo.getPayWayName());
            List<OrderSku> orderSkuList = orderSkuService.findSkuListByOrderNo(exportOrderInfo.getOrderNo());
            if(!CollectionUtils.isEmpty(orderSkuList)){
                List<Integer> skuIdList = orderSkuList.stream().map(OrderSku::getSkuId).distinct().collect(Collectors.toList());
                List<String> skuNameList = skuInfoService.listByIds(skuIdList).stream().map(SkuInfo::getSkuName).collect(Collectors.toList());
                String skuNames = Strings.join(skuNameList,',');
                exportOrderInfo.setOrderSkus(skuNames);
            }
            return exportOrderInfo;
        }).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("data", exportOrderInfoList);
        return new ModelAndView(new SkuOrderView(), map);
    }




    /*@PostMapping(value = "/sendOutOrder")
    @ApiOperation(value = "订单自提",httpMethod = "POST")
    public ResponseData fetchOrder(@RequestBody UpdateOrderFetchStatusDto updateOrderFetchStatusDto){
        boolean result = orderService.updateExpressStatus(updateOrderFetchStatusDto);
        return result ? ResponseData.OK() : ResponseData.ERROR("订单自提失败!");
    }*/


}
