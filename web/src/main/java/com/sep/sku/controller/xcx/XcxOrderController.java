package com.sep.sku.controller.xcx;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.sku.dto.*;
import com.sep.sku.service.OrderLogisticsService;
import com.sep.sku.service.OrderService;
import com.sep.sku.service.OrderSkuService;
import com.sep.sku.tool.Paytool;
import com.sep.sku.vo.*;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 小程序端 订单 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/xcx/order")
@Api(value = "小程序订单相关API", tags = {"小程序订单相关API"})
public class XcxOrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLogisticsService orderLogisticsService;


    @Autowired
    private OrderSkuService orderSkuService;

    @PostMapping(value = "/commitOrder")
    @ApiOperation(value = "提交订单", httpMethod = "POST")
    public ResponseData<CommitOrderRespVo> commitOrder(@RequestBody CommitOrderDto commitOrderDto,
                                                       HttpServletRequest request) {
        commitOrderDto.setIp(Paytool.getRemoteAddrIp(request));
        return ResponseData.OK(orderService.commitOrder(commitOrderDto));
    }


    @PostMapping(value = "/pageQueryOrderInfo")
    @ApiOperation(value = "分页查询订单信息", httpMethod = "POST")
    public ResponseData<IPage<SearchOrderRespVo>> pageQueryOrderInfo(@RequestBody SearchSkuOrderDto searchSkuOrderDto) {
        int userId = (int) JwtUtils.parseJWT(searchSkuOrderDto.getToken()).get("id");
        searchSkuOrderDto.setUserId(userId);
        return ResponseData.OK(orderService.pageSearchSkuOrderInfo(searchSkuOrderDto));
    }

    @PostMapping(value = "/pageSearchOrderSkuResp")
    @ApiOperation(value = "分页查询待评价信息", httpMethod = "POST")
    public ResponseData<IPage<OrderSkuDto>> pageSearchOrderSkuResp(@RequestBody SearchOrderSkuDto SearchOrderSkuDto) {
        return ResponseData.OK(orderSkuService.pageSearchOrderSkuResp(SearchOrderSkuDto));
    }

    @PostMapping(value = "/updateEvaluateStatus")
    @ApiOperation(value = "设置已评价", httpMethod = "POST")
    public ResponseData<Integer> updateEvaluateStatus(@RequestBody IdDto idDto) {
        return ResponseData.OK(orderSkuService.updateEvaluateStatus(idDto.getId()));
    }

    @PostMapping(value = "/updateOrderSkuStatus")
    @ApiOperation(value = "修改订单商品状态", httpMethod = "POST")
    public ResponseData updateOrderSkuStatus(@RequestBody UpdateOrderSkuStatusDto updateDto) {
        if (updateDto == null || CollectionUtils.isEmpty(updateDto.getIds())) {
            return ResponseData.ERROR("id为空");
        }
        boolean result = orderService.updateOrderSkuStatus(updateDto);
        return result ? ResponseData.OK() : ResponseData.ERROR("状态更新失败");
    }

    @PostMapping(value = "/updateOrderStatus")
    @ApiOperation(value = "修改订单状态", httpMethod = "POST")
    public ResponseData updateOrderSkuStatus(@RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        return orderService.updateOrderStatus(updateOrderStatusDto) > 0 ? ResponseData.OK() : ResponseData.ERROR("状态更新失败");
    }

    @PostMapping(value = "/gotoPay")
    @ApiOperation(value = "去支付", httpMethod = "POST")
    public ResponseData<CommitOrderRespVo> gotoPay(@RequestBody GotoPayDto gotoPayDto,
                                                   HttpServletRequest request) {
        gotoPayDto.setIp(Paytool.getRemoteAddrIp(request));
        return ResponseData.OK(orderService.gotoPay(gotoPayDto));
    }

    @PostMapping(value = "/searchOrderDetail")
    @ApiOperation(value = "查看订单详情", httpMethod = "POST")
    public ResponseData<SearchOrderRespVo> searchOrderDetail(@RequestBody SearchOrderDetailDto searchOrderDetailDto) {
        return ResponseData.OK(orderService.searchOrderDetail(searchOrderDetailDto));
    }

    @PostMapping(value = "/consumeRecord")
    @ApiOperation(value = "查看消费记录", httpMethod = "POST")
    public ResponseData<ConsumeRecordRespVo> consumeRecord(@RequestBody ConsumeRecordDto consumeRecordDto) {
        return ResponseData.OK(orderService.consumeRecord(consumeRecordDto));
    }

//    @PostMapping(value = "/searchOrderLogisticsList")
//    @ApiOperation(value = "查询订单物流信息", httpMethod = "POST")
//    public ResponseData<List<SearchOrderLogisticsRespVo>> searchOrderLogisticsList(@RequestBody SearchOrderDetailDto searchOrderDetailDto) {
//        return ResponseData.OK(orderLogisticsService.searchOrderLogisticsList(searchOrderDetailDto));
//    }
}
