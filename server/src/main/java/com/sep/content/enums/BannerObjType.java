package com.sep.content.enums;

import com.sep.common.enums.CommonEnum;

public enum BannerObjType implements CommonEnum<BannerObjType> {

    ACTIVITY(1, "活动"),
    ARTICLE(2, "资讯"),
    SKU(3, "商品");

    private final int code;
    private final String description;

    BannerObjType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static BannerObjType valueOf(int code) {
        for (BannerObjType item : BannerObjType.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        throw new IllegalArgumentException("未知的");
    }

}