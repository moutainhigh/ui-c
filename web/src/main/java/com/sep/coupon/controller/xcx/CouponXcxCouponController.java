package com.sep.coupon.controller.xcx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.coupon.dto.*;
import com.sep.coupon.service.CouponService;
import com.sep.coupon.service.ReceiveRecordService;
import com.sep.coupon.vo.CouponUnreceivedVo;
import com.sep.coupon.vo.MyCanUseCouponVo;
import com.sep.coupon.vo.MyCouponVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 小程序端优惠卷相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-10
 */
@Api(value = "小程序端优惠卷相关API", tags = {"小程序端优惠卷相关API"})
@RestController
@RequestMapping("/coupon/xcx/coupon")
public class CouponXcxCouponController {

    @Resource
    private CouponService couponService;
    @Resource
    private ReceiveRecordService receiveRecordService;

//    @ApiOperation(value = "首页列表查询", httpMethod = "POST")
//    @PostMapping(value = "/nominates")
//    public ResponseData<IPage<CouponUnreceivedVo>> nominates(@RequestBody NominatesDto dto) {
//        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
//        dto.setUserId(userId);
//        return ResponseData.OK(couponService.nominates(dto));
//    }
//
//    @ApiOperation(value = "可领取列表查询", httpMethod = "POST")
//    @PostMapping(value = "/unreceived")
//    public ResponseData<IPage<CouponUnreceivedVo>> unreceived(@RequestBody ReceivedDto dto) {
//        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
//        dto.setUserId(userId);
//        return ResponseData.OK(couponService.unreceived(dto));
//    }

//    @ApiOperation(value = "我的优惠卷列表查询", httpMethod = "POST")
//    @PostMapping(value = "/my")
//    public ResponseData<IPage<MyCouponVo>> myCoupon(@RequestBody MyCouponSearchDto dto) {
//        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
//        dto.setUserId(userId);
//        return ResponseData.OK(receiveRecordService.myCoupon(dto));
//    }

//    @ApiOperation(value = "我可以使用的优惠卷列表查询", httpMethod = "POST")
//    @PostMapping(value = "/can/use")
//    public ResponseData<List<MyCanUseCouponVo>> canUse(@RequestBody MyCanUseCouponSearchDto dto) {
//        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
//        dto.setUserId(userId);
//        return ResponseData.OK(receiveRecordService.canUse(dto));
//    }

//    @ApiOperation(value = "我可以使用的优惠卷数量查询", httpMethod = "POST")
//    @PostMapping(value = "/can/use/count")
//    public ResponseData<Integer> canUseCount(@RequestBody MyCanUseCouponSearchDto dto) {
//        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
//        dto.setUserId(userId);
//        return ResponseData.OK(receiveRecordService.canUseCount(dto));
//    }

//    @PutMapping(value = "/receive")
//    @ApiOperation(value = "领取优惠卷", httpMethod = "PUT")
//    public ResponseData<Boolean> receive(@RequestBody ReceiveDto dto) {
//        int userId = (int) JwtUtils.parseJWT(dto.getToken()).get("id");
//        dto.setUserId(userId);
//        return ResponseData.OK(couponService.receive(dto));
//    }

}