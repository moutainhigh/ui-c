package com.sep.point.controller.server;

import com.sep.common.model.response.ResponseData;
import com.sep.point.dto.PointCurrentInput;
import com.sep.point.dto.PointIncreaseInput;
import com.sep.point.dto.ProductsExchangeInput;
import com.sep.point.service.PointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * Server端积分相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-13
 */
@Api(value = "Server端积分相关API", tags = {"Server端积分相关API"})
@RestController
@RequestMapping("/point/server/point")
public class PointServerPointController {

    @Resource
    private PointService pointService;

    @PostMapping(value = "/productsExchange")
    @ApiOperation(value = "兑换商品", httpMethod = "POST")
    public ResponseData<Boolean> productsExchange(@RequestBody ProductsExchangeInput input) {
        return ResponseData.OK(pointService.productsExchange(input));
    }

    @PostMapping(value = "/increase")
    @ApiOperation(value = "增加积分", httpMethod = "POST")
    public ResponseData<Boolean> increase(@RequestBody PointIncreaseInput input) {
        return ResponseData.OK(pointService.increase(input));
    }

    @PostMapping(value = "/current")
    @ApiOperation(value = "剩余积分", httpMethod = "POST")
    public ResponseData<Integer> current(@RequestBody PointCurrentInput input) {
        return ResponseData.OK(pointService.current(input.getUserId()));
    }

}