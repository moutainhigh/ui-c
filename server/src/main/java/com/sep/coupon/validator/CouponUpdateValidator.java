package com.sep.coupon.validator;

import com.sep.common.exceptions.SepCustomException;
import com.sep.coupon.dto.CouponUpdateDto;
import com.sep.coupon.enums.BizErrorCode;
import com.sep.coupon.enums.PublishStatus;
import com.sep.coupon.model.Coupon;
import com.sep.coupon.service.CouponService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CouponUpdateValidator {

    @Resource
    private CouponService couponService;

    public void validator(CouponUpdateDto dto) {
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
        Coupon coupon = couponService.getById(dto.getId());
        if (coupon.getPublishStatus() != PublishStatus.UNPUBLISHED.getCode()) {
            throw new SepCustomException(BizErrorCode.COUPON_UPDATE_PUBLISH_STATUS_ERROR);
        }
    }

}