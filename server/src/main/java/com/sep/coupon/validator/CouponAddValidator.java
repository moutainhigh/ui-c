package com.sep.coupon.validator;

import com.sep.common.exceptions.SepCustomException;
import com.sep.coupon.dto.CouponAddDto;
import com.sep.coupon.enums.BizErrorCode;
import com.sep.coupon.service.CouponService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CouponAddValidator {

    @Resource
    private CouponService couponService;

    public void validator(CouponAddDto dto) {
        if (dto.getReceiveStartDate().isAfter(dto.getReceiveEndDate())) {
            throw new SepCustomException(BizErrorCode.COUPON_RECEIVE_START_GT_END_ERROR);
        }
        if (dto.getUseStartDate().isAfter(dto.getUseEndDate())) {
            throw new SepCustomException(BizErrorCode.COUPON_USE_START_GT_END_ERROR);
        }
        if (dto.getUseStartDate().isBefore(dto.getReceiveStartDate())) {
            throw new SepCustomException(BizErrorCode.COUPON_USE_START_LT_RECEIVE_START);
        }
        if (dto.getUseEndDate().isBefore(dto.getReceiveEndDate())) {
            throw new SepCustomException(BizErrorCode.COUPON_USE_END_LT_RECEIVE_END);
        }
    }

}