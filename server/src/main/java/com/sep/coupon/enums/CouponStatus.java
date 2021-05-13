package com.sep.coupon.enums;

import com.sep.common.enums.CommonEnum;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum CouponStatus implements CommonEnum<CouponStatus> {

    UN_USED(0, "未使用"),
    USED(1, "已使用"),
    STALE(2, "已过期");

    private final int code;
    private final String description;

    CouponStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CouponStatus valueOf(int code) {
        for (CouponStatus item : CouponStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}