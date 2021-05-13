package com.sep.coupon.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.coupon.dto.BaseUpdateDto;
import com.sep.coupon.dto.CouponAddDto;
import com.sep.coupon.dto.CouponPageSearchDto;
import com.sep.coupon.dto.CouponUpdateDto;
import com.sep.coupon.service.CouponService;
import com.sep.coupon.vo.CouponDetailsOutPut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 后端优惠卷相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-10
 */
@Api(value = "后端优惠卷相关API", tags = {"后端优惠卷相关API"})
@RestController
@RequestMapping("/coupon/background/coupon")
public class CouponBackgroundCouponController {

    @Resource
    private CouponService couponService;

//    @ApiOperation(value = "分页查询", httpMethod = "POST")
//    @PostMapping(value = "/pageSearch")
//    public ResponseData<IPage<CouponDetailsOutPut>> pageSearch(@RequestBody CouponPageSearchDto dto) {
//        return ResponseData.OK(couponService.pageSearch(dto));
//    }
//
//    @PostMapping(value = "/add")
//    @ApiOperation(value = "添加", httpMethod = "POST")
//    public ResponseData<Boolean> add(@RequestBody CouponAddDto dto) {
//        return ResponseData.OK(couponService.add(dto));
//    }

//    @PutMapping(value = "/update")
//    @ApiOperation(value = "修改", httpMethod = "PUT")
//    public ResponseData<Boolean> update(@RequestBody CouponUpdateDto dto) {
//        return ResponseData.OK(couponService.update(dto));
//    }
//
//    @PutMapping(value = "/publish")
//    @ApiOperation(value = "发行", httpMethod = "PUT")
//    public ResponseData<Boolean> publish(@RequestBody BaseUpdateDto dto) {
//        return ResponseData.OK(couponService.publish(dto));
//    }
//
//    @PutMapping(value = "/suspended")
//    @ApiOperation(value = "停发", httpMethod = "PUT")
//    public ResponseData<Boolean> suspended(@RequestBody BaseUpdateDto dto) {
//        return ResponseData.OK(couponService.suspended(dto));
//    }
//
//    @GetMapping(value = "/{id}")
//    @ApiOperation(value = "详情", httpMethod = "GET")
//    public ResponseData<CouponDetailsOutPut> details(@PathVariable Integer id) {
//        return ResponseData.OK(couponService.details(id));
//    }

}