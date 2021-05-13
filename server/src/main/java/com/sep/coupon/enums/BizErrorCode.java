package com.sep.coupon.enums;

import com.sep.common.enums.CommonEnum;
import com.sep.common.enums.ErrorCode;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum BizErrorCode implements CommonEnum<BizErrorCode>, ErrorCode {

    COUPON_ADD_ERROR(1001, "优惠卷保存失败"),
    COUPON_UPDATE_ERROR(1002, "优惠卷修改失败"),
    COUPON_PUBLISHED_ERROR(1003, "优惠卷发布失败"),
    COUPON_SUSPENDED_ERROR(1004, "优惠卷停发失败"),
    COUPON_UPDATE_PUBLISH_STATUS_ERROR(1005, "已经发布过的优惠卷不可以再修改"),
    COUPON_PUBLISH_PUBLISH_STATUS_ERROR(1006, "已经发布过的优惠卷不可以再发布"),
    COUPON_RCEIVE_ERROR(1007, "优惠卷领取失败"),
    COUPON_USE_ERROR(1008, "优惠卷使用失败"),
    COUPON_RECEIVE_START_GT_END_ERROR(1009, "领取有效期开始时间不能大于领取有效期结束时间"),
    COUPON_USE_START_GT_END_ERROR(1010, "使用有效期开始时间不能大于使用有效期结束时间"),
    COUPON_USE_START_LT_RECEIVE_START(1011, "使用有效期开始时间不能小于领取有效期开始时间"),
    COUPON_USE_END_LT_RECEIVE_END(1012, "使用有效期结束时间不能小于领取有效期结束时间"),
    COUPON_PUBLISH_OVERDUE(1013, "该优惠卷已经过期不可以发布"),
    COUPON_PUBLISH_INVENTORY_ZERO(1014, "该优惠卷数量为0不可以发布"),
    COUPON_RECEIVED(1015, "该优惠卷只能被领取一次");

    private final int code;
    private final String description;

    BizErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return description;
    }

    public String getDescription() {
        return description;
    }

    public static BizErrorCode valueOf(int code) {
        for (BizErrorCode item : BizErrorCode.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}