package com.sep.sku.controller.background;


import com.sep.sku.dto.HomeOrderStatisticalDto;
import com.sep.sku.service.OrderService;
import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 首页统计
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/background/home")
@Api(value = "后台首页统计相关API", tags = {"后台首页统计相关API"})
public class BackgroundHomeController extends BaseController{

    @Autowired
    private OrderService orderService;

    @PostMapping(value= "/order_statistical")
    @ApiOperation(value = "订单统计信息",httpMethod = "POST")
    public ResponseData orderStatistical(@RequestBody HomeOrderStatisticalDto orderStatisticalDto){
        return ResponseData.OK(orderService.homeOrderAndSkuStatistical(orderStatisticalDto.getDate()));
    }

}
