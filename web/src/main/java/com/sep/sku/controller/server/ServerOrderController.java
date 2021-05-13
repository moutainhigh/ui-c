package com.sep.sku.controller.server;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sep.sku.dto.CounselorStatisticalOrderDto;
import com.sep.sku.dto.ServerBatchSearchOrderDto;
import com.sep.sku.dto.StatisticalOrderDto;
import com.sep.sku.vo.CounselorStatisticalOrderRespVo;
import com.sep.sku.vo.HomeOrderStatisticalRespVo;
import com.sep.sku.vo.ServerSearchOrderRespVo;
import com.sep.sku.vo.StatisticalOrderRespVo;
import com.sep.sku.service.OrderService;
import com.sep.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 服务端 订单 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/server/order")
@Api(value = "服务端订单相关API", tags = {"服务端订单相关API"})
@Slf4j
public class ServerOrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    /**
     * 统计用户订单信息
     * */
    @PostMapping(value = "/statistical")
    @ApiOperation(value = "统计订单信息",httpMethod = "POST")
    StatisticalOrderRespVo statisticalOrder(@RequestBody StatisticalOrderDto statisticalOrderDto){
        log.info("[statisticalOrder],statisticalOrderDto:{}", JSON.toJSONString(statisticalOrderDto));
        return orderService.statisticalOrder(statisticalOrderDto);
    }

    /**
     * 统计销售代表订单信息
     * */
    @PostMapping(value = "/counselor_statistical")
    @ApiOperation(value = "统计销售代表订单信息",httpMethod = "POST")
    CounselorStatisticalOrderRespVo statisticalCounselorOrder(@RequestBody CounselorStatisticalOrderDto statisticalOrderDto){
        log.info("[statisticalCounselorOrder],statisticalOrderDto:{}", JSON.toJSONString(statisticalOrderDto));
        return orderService.counselorStatisticalOrder(statisticalOrderDto);
    }

    @PostMapping(value = "/pageSearchSkuOrderInfo")
    @ApiOperation(value = "分页查询商品订单信息", httpMethod = "POST")
    public Page<ServerSearchOrderRespVo> pageSearchCardOrderInfo(@RequestBody ServerBatchSearchOrderDto searchOrderDto){
        Page<ServerSearchOrderRespVo> orderRespVoPage = orderService.serverPageSearchSkuOrderInfo(searchOrderDto);
        return orderRespVoPage;
    }

    @PostMapping(value = "/homeStatistical")
    @ApiOperation(value = "后台首页统计信息", httpMethod = "POST")
    public HomeOrderStatisticalRespVo homeStatistical(@RequestBody String date){
        return orderService.homeOrderAndSkuStatistical(date);
    }
}
