package com.sep.coupon.enums;

import com.sep.common.enums.CommonEnum;

/**
 * @author litao, 2018/12/29
 * @version 1.0
 */
public enum PublishStatus implements CommonEnum<PublishStatus> {

    UNPUBLISHED(0, "待发行"),
    PUBLISHED(1, "发行中"),
    SUSPENDED(2, "已停发");

    private final int code;
    private final String description;

    PublishStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PublishStatus valueOf(int code) {
        for (PublishStatus item : PublishStatus.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}