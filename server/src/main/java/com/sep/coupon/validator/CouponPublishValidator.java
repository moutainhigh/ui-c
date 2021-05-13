package com.sep.coupon.validator;

import com.sep.common.exceptions.SepCustomException;
import com.sep.coupon.dto.BaseUpdateDto;
import com.sep.coupon.enums.BizErrorCode;
import com.sep.coupon.enums.PublishStatus;
import com.sep.coupon.model.Coupon;
import com.sep.coupon.service.CouponService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

@Component
public class CouponPublishValidator {

    @Resource
    private CouponService couponService;

    public void validator(BaseUpdateDto dto) {
        Coupon coupon = couponService.getById(dto.getId());
        if (coupon.getReceiveEndDate().isBefore(LocalDate.now())) {
            throw new SepCustomException(BizErrorCode.COUPON_PUBLISH_OVERDUE);
        }
        if (coupon.getTotal() <= 0) {
            throw new SepCustomException(BizErrorCode.COUPON_PUBLISH_INVENTORY_ZERO);
        }
        if (coupon.getPublishStatus() != PublishStatus.UNPUBLISHED.getCode()) {
            throw new SepCustomException(BizErrorCode.COUPON_PUBLISH_PUBLISH_STATUS_ERROR);
        }
    }

}