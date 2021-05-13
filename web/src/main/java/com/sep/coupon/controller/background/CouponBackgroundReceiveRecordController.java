package com.sep.coupon.controller.background;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.common.model.response.ResponseData;
import com.sep.coupon.dto.ReceiveRecordPageSearchDto;
import com.sep.coupon.dto.UseRecordPageSearchDto;
import com.sep.coupon.service.ReceiveRecordService;
import com.sep.coupon.vo.ReceiveRecordVo;
import com.sep.coupon.vo.UseRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 后端优惠卷领取/使用记录相关API
 * </p>
 *
 * @author litao
 * @since 2020-02-10
 */
@Api(value = "后端优惠卷领取/使用记录相关API", tags = {"后端优惠卷领取/使用记录相关API"})
@RestController
@RequestMapping("/coupon")
public class CouponBackgroundReceiveRecordController {

    @Resource
    private ReceiveRecordService receiveRecordService;

    @ApiOperation(value = "优惠卷领取记录分页查询", httpMethod = "POST")
    @PostMapping(value = "/background/receive/record")
    public ResponseData<IPage<ReceiveRecordVo>> receiveRecordPageSearch(@RequestBody ReceiveRecordPageSearchDto dto) {
        return ResponseData.OK(receiveRecordService.receiveRecordPageSearch(dto));
    }

    @ApiOperation(value = "优惠卷使用记录分页查询", httpMethod = "POST")
    @PostMapping(value = "/background/use/record")
    public ResponseData<IPage<UseRecordVo>> useRecordPageSearch(@RequestBody UseRecordPageSearchDto dto) {
        return ResponseData.OK(receiveRecordService.useRecordPageSearch(dto));
    }

}