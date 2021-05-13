package com.sep.coupon.controller.server;


import com.sep.common.model.response.ResponseData;
import com.sep.coupon.dto.GetCouponInput;
import com.sep.coupon.dto.UseCouponInput;
import com.sep.coupon.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 后端优惠卷相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-10
 */
@Api(value = "Server优惠卷相关API", tags = {"Server优惠卷相关API"})
@RestController
@RequestMapping("/coupon/server/coupon")
public class CouponServer {

    @Resource
    private CouponService couponService;

//    @PostMapping(value = "/use")
//    @ApiOperation(value = "使用优惠卷", httpMethod = "PUT")
//    public ResponseData<Boolean> use(@RequestBody UseCouponInput input) {
//        return ResponseData.OK(couponService.use(input));
//    }
//
//    @PostMapping(value = "/")
//    @ApiOperation(value = "使用优惠卷", httpMethod = "POST")
//    public ResponseData<Boolean> details(@RequestBody GetCouponInput input) {
//        return ResponseData.OK(couponService.details(input.getCouponId()));
//    }

}